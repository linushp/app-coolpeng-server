package com.coolpeng.framework.db;

import com.coolpeng.framework.event.TMSEvent;

/**
 * Created by 栾海鹏 on 2016/3/31.
 */
public class SimpleDAOEvent extends TMSEvent {

    private SimpleDAOEventEnum eventType;
    private Class clazz;


    public SimpleDAOEvent(SimpleDAOEventEnum eventEnum, Class clazz, Object entity) {
        this.eventType = eventEnum;
        this.clazz = clazz;
        this.setData(entity);
        this.setName(eventEnum.toString());
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }


    public SimpleDAOEventEnum getEventType() {
        return eventType;
    }

    public void setEventType(SimpleDAOEventEnum eventType) {
        this.eventType = eventType;
    }
}
