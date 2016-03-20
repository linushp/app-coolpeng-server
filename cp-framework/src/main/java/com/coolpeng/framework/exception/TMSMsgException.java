package com.coolpeng.framework.exception;

/**
 * Created by 栾海鹏 on 2016/3/16.
 */
public class TMSMsgException extends Exception {

    private int errorCode = 10000;

    private String message;

    public TMSMsgException(String message) {
        this.message = message;
    }

    public TMSMsgException(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
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
}
