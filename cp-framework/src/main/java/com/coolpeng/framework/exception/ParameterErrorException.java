package com.coolpeng.framework.exception;

/**
 * Created by Administrator on 2015/9/30.
 */
public class ParameterErrorException extends Exception {
    private String msg;
    public ParameterErrorException(String msg) {
        this.msg=msg;
    }

    @Override
    public String toString() {
        return "ParameterErrorException{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
