package com.coolpeng.framework.db;

/**
 * Created by 栾海鹏 on 2016/3/31.
 */
public enum SimpleDAOEventEnum {

    beforeInsert("beforeInsert"),
    afterInsert("afterInsert"),
    beforeUpdate("beforeUpdate"),
    afterUpdate("afterUpdate"),
    beforeUpdateFields("beforeUpdateFields"),
    afterUpdateFields("afterUpdateFields");

    private String str;

    SimpleDAOEventEnum(String str){
        this.str = str;
    }

    public String toString(){
        return this.str;
    }
}
