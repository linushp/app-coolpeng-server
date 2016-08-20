package com.coolpeng.blog.entity.enums;

import com.coolpeng.framework.db.QueryCondition;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/20.
 */
public enum AccessControl {

    PUBLIC("public"),
    PRIVATE("private");

    private String value;

    private AccessControl(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return this.getValue();
    }

    public boolean isPublic(){
        return this == AccessControl.PUBLIC;
    }

    public static AccessControl parse(String m){
        if (PUBLIC.getValue().equalsIgnoreCase(m)){
            return PUBLIC;
        }else {
            return PRIVATE;
        }
    }


    public static void appendQueryCondition(AccessControl accessControl,QueryCondition qc){
        if (accessControl==null || qc==null){
            return;
        }

        if(accessControl.isPublic()){
            qc.addEqualCondition("accessControl",PUBLIC.getValue());
        }
    }

    public static void appendQueryCondition(AccessControl accessControl,Map qc){

        if (accessControl==null || qc==null){
            return;
        }

        if(accessControl.isPublic()){
            qc.put("accessControl", PUBLIC.getValue());
        }
    }


}