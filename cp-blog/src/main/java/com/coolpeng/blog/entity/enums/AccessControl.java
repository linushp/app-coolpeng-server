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


    public void appendQueryCondition(QueryCondition qc){
        if(isPublic()){
            qc.addEqualCondition("accessControl",PUBLIC.getValue());
        }
    }

    public void appendQueryCondition(Map qc){
        if(isPublic()){
            qc.put("accessControl",PUBLIC.getValue());
        }
    }


}