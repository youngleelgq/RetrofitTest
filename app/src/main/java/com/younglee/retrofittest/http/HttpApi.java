package com.younglee.retrofittest.http;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * author younglee
 * Description：retrofit的网络请求操作类，主要进行请求的配置
 * DataTime 2017/2/21 14:17
 */
public interface HttpApi {
    @Headers({"Content-type:application/json;charset=utf-8", "Accept: application/json"})
    @POST("/")
    Observable<String> test(@Body String body);//总报文

    @Headers({"Content-type:application/json;charset=utf-8", "Accept: application/json"})
    @POST("/")
    Call<String> test2(@Body String body);//总报文
}
