package com.coolpeng.framework.event;


import com.coolpeng.framework.utils.StringUtils;

/**
 * Created by 栾海鹏 on 2015/12/4.
 */
public class TMSEvent {

    private String name;
    private Object data;


    public TMSEvent() {
    }

    public TMSEvent(String name,Object data) {
        this.data = data;
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getName() {
        if (StringUtils.isBlank(name)){
            this.name = this.getClass().getName();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
