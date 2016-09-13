package com.coolpeng.appbase.exception;

import com.coolpeng.framework.exception.TMSMsgException;

/**
 * Created by Administrator on 2016/9/5.
 */
public class OperationFrequencyException  extends TMSMsgException {
    public OperationFrequencyException(String message) {
        super(message, 10003,"OperationFrequencyException");
    }
}
