package com.coolpeng.framework.event;

/**
 * Created by 栾海鹏 on 2015/12/4.
 */
abstract public class TMSEventListener<T extends TMSEvent> {

    /**
     * 监听到事件后会调用此函数
     * @param event
     */
    abstract public void onEvent(T event) throws Exception;

    /**
     * 事件监听器的唯一名字
     * @return 字符串
     */
    public String getName(){
        return this.getClass().getName();
    }
}
