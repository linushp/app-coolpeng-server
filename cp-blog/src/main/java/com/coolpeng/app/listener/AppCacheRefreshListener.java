package com.coolpeng.app.listener;

import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.SimpleDAOEvent;
import com.coolpeng.framework.db.SimpleDAOEventEnum;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventListener;

/**
 * Created by luanhaipeng on 16/7/6.
 */
public class AppCacheRefreshListener extends TMSEventListener {

    @Override
    public void onEvent(TMSEvent event) {
        if (event instanceof SimpleDAOEvent) {
            SimpleDAOEvent daoEvent = (SimpleDAOEvent) event;
            SimpleDAOEventEnum eventType = daoEvent.getEventType();
            if (eventType == SimpleDAOEventEnum.afterInsert) {
                Class entityClass = daoEvent.getClazz();
                Object entity = daoEvent.getData();
                //数据插入表中之后的事件
                onEntityInsert(entityClass, entity);
            }
        }
    }

    private void onEntityInsert(Class entityClass, Object entity) {
        //TODO 数据库中有新数据插入执行的函数
    }


}
