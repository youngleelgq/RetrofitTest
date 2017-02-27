package com.younglee.retrofittest.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.utils.LogUtils;
import com.younglee.retrofittest.http.HttpCallback;
import com.younglee.retrofittest.model.IModel;

/**
 * author younglee
 * Description：
 * DataTime 2017/2/22 10:43
 */
public abstract class BaseActivity extends AppCompatActivity implements HttpCallback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preView();
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void preView();

    @Override
    public void onHttpStart() {
        LogUtils.d("start");
    }

    @Override
    public void onHttpGetDataSucc(IModel datas) {
        LogUtils.d("success");
    }

    @Override
    public void onHttpGetDataFail(String retCode, String msg) {
        LogUtils.e(msg);
    }

    @Override
    public void onHttpError(String retCode, String msg) {
        LogUtils.e(msg);
    }

    @Override
    public void onHttpLoginOut(String retCode, String msg) {
        LogUtils.e(msg);
    }

    @Override
    public void onHttpFinish() {
        LogUtils.d("finish");
    }
}
