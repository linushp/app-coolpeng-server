package com.coolpeng.appbase.exception;

import com.coolpeng.framework.exception.TMSMsgException;

/**
 * Created by Administrator on 2016/9/5.
 */
public class PermissionException  extends TMSMsgException {
    public PermissionException(String message) {
        super(message, 10002,"PermissionException");
    }
}
