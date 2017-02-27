package com.younglee.retrofittest.http;

import android.os.StrictMode;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.younglee.retrofittest.Conver.ToStringConverterFactory;
import com.younglee.retrofittest.common.Const;
import com.younglee.retrofittest.exception.MyHttpException;
import com.younglee.retrofittest.model.TestRequest;
import com.younglee.retrofittest.model.TestResponse;
import com.younglee.retrofittest.safe.SafeUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述: 网络的入口，需要传入请求体和实现了接口的子类对象。
 * 时间：16/7/20 下午4:30
 * 作者: younglee
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class NetWorkHelper {


    private static final String TAG = NetWorkHelper.class.getSimpleName();
    private static String encData1;
    @SuppressWarnings("ConstantConditions")
    private static String ip = Const.getIp();

    /**
     * 异步请求
     *
     * @param request  1
     * @param callback 2
     */
    public static void test(TestRequest request, final HttpCallback callback) {

        /**
         * 这里是添加了RxJava后的方式。需要搞清楚Observer对象里面的每一个方法的含义。
         * */
        //这里HttpLoginRequest可以写成T类型。然后传入的类型是他的子类就OK了。

        String jsonStr = new Gson().toJson(request);
        try {
            LogUtils.d(TAG, "请求内容:" + jsonStr);
            encData1 = SafeUtil.getEncData(jsonStr);//这里吧传进来的请求体先转成json后再进行加密进行传输。
            LogUtils.d(TAG, "加密后为:" + encData1);
        } catch (MyHttpException e) {
            e.printStackTrace();
        }
        Network.getHttpApi().test(encData1)// 其实到这里形成的是个Observable对象。
                .map(new Func1<String, TestResponse>() {//这个方法是吧网络请求回来的string类型的加密串进行解密并还原成对象

                    @Override
                    public TestResponse call(String s) {

                        String content = null;
                        try {
                            LogUtils.d(TAG, "响应内容:" + s);
                            //此处加密
                            content = SafeUtil.getDecData(s);
                        } catch (MyHttpException e) {
                            e.printStackTrace();
                        }
                        return new Gson().fromJson(content, TestResponse.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.onHttpStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定doOnSubscribe在主线程执行
                .observeOn(AndroidSchedulers.mainThread())//在主线程观察。
                .subscribe(new HttpSubscriber<TestResponse>(callback));//订阅到这个对象。前面一变，他就变。这里的Login其实也是指定返回的类型。
    }


    /**
     * 同步请求
     * author younglee
     * create at 2017/2/21 15:14
     */
    public static TestResponse test2(TestRequest req) {

        String jsonStr = new Gson().toJson(req);

        try {
            encData1 = SafeUtil.getEncData(jsonStr);
        } catch (MyHttpException e) {
            e.printStackTrace();
        }


        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(20, TimeUnit.SECONDS);
//        httpClientBuilder.

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(ip)
                .addConverterFactory(new ToStringConverterFactory())
                .build();
        LogUtils.d("ip地址为:" + ip);
        HttpApi movieService = retrofit.create(HttpApi.class);
        Call<String> call = movieService.test2(encData1);//这里执行回来返回的是string类型的数据

        //network can run onMainThread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            String rsp = call.execute().body();

            String content = null;
            try {
                content = SafeUtil.getDecData(rsp);
            } catch (MyHttpException e) {
                e.printStackTrace();
            }

            return new Gson().fromJson(content, TestResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
