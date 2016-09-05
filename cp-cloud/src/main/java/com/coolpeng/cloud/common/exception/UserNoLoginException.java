package com.coolpeng.cloud.common.exception;

import com.coolpeng.framework.exception.TMSMsgException;

/**
 * Created by Administrator on 2016/9/5.
 */
public class UserNoLoginException extends TMSMsgException{

    public UserNoLoginException(String message) {
        super(message, 10001,"UserNoLoginException");
    }
}
