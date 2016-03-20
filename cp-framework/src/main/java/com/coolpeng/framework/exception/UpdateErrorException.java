package com.coolpeng.framework.exception;

/**
 * Created by Administrator on 2015/9/30.
 */
public class UpdateErrorException extends Exception {

    private static final long serialVersionUID = 1L;
    private Object entity;


    public UpdateErrorException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "UpdateErrorException{" +
                "entity=" + entity +
                '}';
    }
}

