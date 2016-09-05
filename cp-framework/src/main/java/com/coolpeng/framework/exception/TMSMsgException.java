package com.coolpeng.framework.exception;

/**
 * Created by 栾海鹏 on 2016/3/16.
 */
public class TMSMsgException extends Exception {

    private int errorCode = 10000;

    private String message;

    private String desc = "TMSMsgException";

    public TMSMsgException(String message) {
        this.message = message;
    }

    public TMSMsgException(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
    public TMSMsgException(String message, int errorCode,String desc) {
        this.message = message;
        this.errorCode = errorCode;
        this.desc = desc;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
