package com.younglee.retrofittest.http;


import android.os.Environment;

import com.blankj.utilcode.utils.LogUtils;
import com.younglee.retrofittest.Conver.ToStringConverterFactory;
import com.younglee.retrofittest.common.Const;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @描述: retrofit的网络请求操作类，主要进行请求的配置
 * @时间：16/7/20 下午4:30
 * @作者: younglee
 */
@SuppressWarnings({"JavaDoc", "SpellCheckingInspection", "WeakerAccess"})
public class Network {


    private static HttpApi httpApi;
    private static final String HTTP_CACHE_FILENAME = "HttpCache";

    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    /**
     * retrofit的标准姿势！获得retrofit对象。用来去掉api里面的方法。假如不用rxjava的话。就用call。执行异步回调也就可以了。
     * 这里要弄清楚Builder后面每个方法都什么含义。
     * 但是我们要和rxjava结合使用的话需要用到observer
     */

    static HttpApi getHttpApi() {
        String ip = Const.getIp();
        if (httpApi == null) {
            httpApi = getRetrofit(ip);
        }
        LogUtils.d("ip地址为:" + ip);
        return httpApi;
    }


    //获取retrofit的对象
    private static HttpApi getRetrofit(String baseUrl) {
        Cache cache;
        String http = Environment.getExternalStorageDirectory().getAbsolutePath();//新生成的图片。
        File httpCacheDirectory = new File(http, HTTP_CACHE_FILENAME);
        cache = new Cache(httpCacheDirectory, 10 * 1024);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().addInterceptor(new LogInterceptor());
        httpClientBuilder.addNetworkInterceptor(new LogInterceptor());
        httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.cache(cache);


        Retrofit build = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(baseUrl)
//                .addConverterFactory(gsonConverterFactory)//这里是转换json。stringConverterFactory
//                .addConverterFactory(StringConverterFactory.create())//这里是转换json。stringConverterFactory
                .addConverterFactory(new ToStringConverterFactory())//这里自定义入参数类型。里面做加解密。

                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
//        callback.onHttpStart();
        return build.create(HttpApi.class);
    }


    //设置过滤器
    private static class LogInterceptor implements Interceptor {
        private Request request;

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
/*-------------------------------------请求提拦截器-----------------------------------------------*/
            request = chain.request();
            Request.Builder builder = request.newBuilder();
            String postBodyString = bodyToString(request.body());
            final CacheControl.Builder builder1 = new CacheControl.Builder();
            builder1.maxAge(10, TimeUnit.MILLISECONDS);
            request = builder
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
                    .build();

/*-------------------------------------返回信息拦截----------------------------------------------*/

            String cacheControl = request.cacheControl().toString();

            okhttp3.Response response = chain.proceed(chain.request());
            MediaType mediaType = response.body().contentType();
            InputStream inputStream = response.body().byteStream();
            String content = Inputstr2Str_byteArr(inputStream, "gbk");
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .body(body)
                        .build();
            } else {
                return response;
            }
        }
    }


    public static String Inputstr2Str_byteArr(InputStream in, String encode) {
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        int len;
        try {
            if (encode == null || encode.equals("")) {
                // 默认以utf-8形式
                encode = "gbk";
            }
            while ((len = in.read(b)) != -1) {
                sb.append(new String(b, 0, len, encode));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }


    public static String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


}
