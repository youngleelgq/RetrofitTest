package com.younglee.retrofittest.http;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.younglee.retrofittest.common.Const;
import com.younglee.retrofittest.model.Response;

import java.net.ConnectException;

import rx.Subscriber;

/**
 * @描述: 网络事件的订阅者，这个类主要是吧订阅者的事件传递到app里面去。
 * @时间：16/7/20 下午4:30
 * @作者: younglee
 */
@SuppressWarnings({"JavaDoc", "SpellCheckingInspection"})
class HttpSubscriber<T extends Response> extends Subscriber<T> {

    private HttpCallback callback;

    HttpSubscriber(HttpCallback callback) {
        this.callback = callback;
    }

    /**
     * 这里不能启动转圈，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程
     */
    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * @描述: 每一个Observerable对象在终结的时候都会调用onCompleted()或onError
     * @时间：16/7/20 下午6:02
     * @作者: younglee
     */
    @Override
    public void onCompleted() {
        callback.onHttpFinish();
    }

    /**
     * @描述: 每一个Observerable对象在终结的时候都会调用onCompleted()或onError
     * @时间：16/7/20 下午6:02
     * @作者: younglee
     */
    @Override
    public void onError(Throwable e) {
        callback.onHttpFinish();

        // 手机不在网络中，会直接报连接异常,请稍后再试
        if (e instanceof ConnectException) {
            onNetworkError(Const.ErrorCode.NETWORK_UNAVAILABLE);
            return;
        }

        LogUtils.e("HttpSubscriber", "++++++++++++++++++++++++++++++++++++++++");
        e.printStackTrace();
        LogUtils.e("HttpSubscriber", "++++++++++++++++++++++++++++++++++++++++");

        onNetworkError(Const.ErrorCode.NETWORK_CONN_ERROR);

    }

    /**
     * @描述: 每一个Observerable对象在终结的时候都会调用onCompleted()或onError
     * @时间：16/7/20 下午6:02
     * @作者: 程兴江
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onNext(T t) {
        if (t == null) {
            onNetworkError(Const.ErrorCode.NETWORK_CONN_ERROR);
        } else if (Const.RetCode.SUCCESS.equals(t.getRetCode())) {
            callback.onHttpGetDataSucc(t);
        } else if (Const.RetCode.LOGIN_TIMEOUT.equals(t.getRetCode())) {
            callback.onHttpLoginOut(t.getRetCode(), t.getMemo());
        } else {
            if (TextUtils.isEmpty(t.getMemo()))
                onNetworkError(Const.ErrorCode.NETWORK_CONN_ERROR);
            else
                callback.onHttpGetDataFail(t.getRetCode(), t.getMemo());
        }
    }


    private void onNetworkError(String error) {
        callback.onHttpError(error, Const.errorMap.get(error));
        Log.e("网络错误原因", error);
    }

}
