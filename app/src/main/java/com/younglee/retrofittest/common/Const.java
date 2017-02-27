package com.younglee.retrofittest.common;

import java.util.HashMap;
import java.util.Map;

/**
 * author younglee
 * Description：常量类
 * DataTime 2017/2/21 14:50
 */
@SuppressWarnings({"ConstantConditions", "SpellCheckingInspection"})
public class Const {
    public static Map<String, String> errorMap = new HashMap<>();

    public interface Config {
        boolean IS_PROD = false;
        boolean DEV_DEBUG = true;
    }

    public static String getIp() {
        return Const.Config.IS_PROD ? Ip.API_MAC_PRO : Ip.API_MAC_DEV;
    }

    interface Ip {
        String API_MAC_PRO = "http://222.173.121.30:40200";
        String API_MAC_DEV = "http://103.226.132.194:9210";
    }

    /**
     * @描述: 错误码
     * @时间：16/5/25 上午10:41
     * @作者:
     */
    @SuppressWarnings("JavaDoc")
    public interface ErrorCode {
        /**
         * 返回成功
         */
        String NETWORK_UNAVAILABLE = "10001";
        /**
         * 正在处理中
         */
        String NETWORK_CONN_ERROR = "19999";
    }

    static {
        errorMap.put(ErrorCode.NETWORK_UNAVAILABLE, "网络不可用，请检查网络配置后重试！");
        errorMap.put(ErrorCode.NETWORK_CONN_ERROR, "网络异常，请稍后再试！");

    }

    /**
     * @author yangningbo
     * @ClassName: RetCode
     * @Description: 远程数据访问返回码
     * @date 2015-3-25 下午1:36:41
     */
    @SuppressWarnings("JavaDoc")
    public interface RetCode {
        /**
         * 返回成功
         */
        String SUCCESS = "0000";
        /**
         * 登录超时
         */
        String LOGIN_TIMEOUT = "#";
    }

}
