package com.younglee.retrofittest.http;


import com.younglee.retrofittest.model.IModel;

/**
 * 定义数据层的回调接口
 * 可以理解成一个Callback，在model中获取到数据后，通过这个callback把信息回传到presenter层，
 * 所以在presenter层要实现这个interface
 * 泛型T定义的是返回给presenter的数据类型，具体到response
 * by y on 2016/5/27.
 */
@SuppressWarnings("SpellCheckingInspection")
public interface HttpCallback<T extends IModel> {//

    // 获取数据之前
    void onHttpStart();

    // 获取到数据成功的回调
    void onHttpGetDataSucc(T datas);

    // 获取到数据失败的回调
    void onHttpGetDataFail(String retCode, String msg);

    // 用户登录超时
    void onHttpLoginOut(String retCode, String msg);

    // 失败的回调
    void onHttpError(String retCode, String msg);

    // 结束后的回调
    void onHttpFinish();


}
