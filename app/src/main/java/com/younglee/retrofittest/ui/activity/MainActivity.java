package com.younglee.retrofittest.ui.activity;

import android.os.Bundle;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.younglee.retrofittest.R;
import com.younglee.retrofittest.common.Const;
import com.younglee.retrofittest.http.NetWorkHelper;
import com.younglee.retrofittest.model.IModel;
import com.younglee.retrofittest.model.TestRequest;
import com.younglee.retrofittest.safe.Tag;
import com.younglee.retrofittest.ui.base.BaseActivity;

import java.util.Date;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestTest();
    }

    @Override
    protected void initData() {
        LogUtils.d("密钥为:" + Tag.getTag(Const.Config.DEV_DEBUG));
        LogUtils.d("时间为:" + TimeUtils.date2String(new Date(System.currentTimeMillis()), "yyyyMMddHHmmss"));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void preView() {
        setContentView(R.layout.activity_main);
    }

    private void requestTest() {
        NetWorkHelper.test(new TestRequest(), this);
    }

    @Override
    public void onHttpGetDataSucc(IModel datas) {
        super.onHttpGetDataSucc(datas);
    }

    @Override
    public void onHttpError(final String retCode, final String msg) {
        super.onHttpError(retCode, msg);
    }
}
