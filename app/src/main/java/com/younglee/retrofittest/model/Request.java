package com.younglee.retrofittest.model;

/**
 * author younglee
 * Description：
 * DataTime 2017/2/21 15:38
 */
public class Request implements IModel {
    protected String funCode;
    protected String reqDate;
    protected String reqTime;

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }
}
