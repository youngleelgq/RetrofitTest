package com.younglee.retrofittest.utils;

import com.blankj.utilcode.utils.TimeUtils;

import java.util.Date;

/**
 * author younglee
 * Description：
 * DataTime 2017/2/22 10:18
 */
public class MyTimeUtils {
    public static final String PATTERN = "yyyyMMddHHmmss";

    /**
     * 201702221020
     * author younglee
     * create at 2017/2/22 10:20
     */
    public static String getDefaultTimeAndTime() {
        return TimeUtils.date2String(new Date(System.currentTimeMillis()), PATTERN);
    }

    public static String getDefaultDate() {
        return getDefaultTimeAndTime().substring(0, 8);
    }

    public static String getDefaultTime() {
        return getDefaultTimeAndTime().substring(8);
    }
}
