package com.younglee.retrofittest.model;

/**
 * author younglee
 * Description：
 * DataTime 2017/2/21 15:40
 */
public class Response implements IModel {
    protected String retCode;
    protected String Memo;
    protected String funCode;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }
}
