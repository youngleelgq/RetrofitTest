package com.younglee.retrofittest.model;

import com.younglee.retrofittest.utils.MyTimeUtils;

/**
 * author younglee
 * Description：
 * DataTime 2017/2/21 14:55
 */
@SuppressWarnings("SpellCheckingInspection")
public class TestRequest extends Request {
    private String appType;
    private String orderStart;
    private String number;

    private String sessionId;
    private String calling;

    public TestRequest() {
        setFunCode("CJWT");
        setCalling("15727308901");
        setReqTime(MyTimeUtils.getDefaultTime());
        setReqDate(MyTimeUtils.getDefaultDate());
        setAppType("0");
        setOrderStart("1");
        setNumber("100");
    }

    public String getCalling() {
        return calling;
    }

    public void setCalling(String calling) {
        this.calling = calling;
    }


    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(String orderStart) {
        this.orderStart = orderStart;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
