package com.coolpeng.framework.event;

import com.coolpeng.framework.qtask.QueueTask;
import com.coolpeng.framework.qtask.QueueTaskRunner;
import com.coolpeng.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件总线类
 * Created by 栾海鹏 on 2015/12/4.
 */
public class TMSEventBus {

    private static final Logger logger = LoggerFactory.getLogger(TMSEventBus.class);

    private static final QueueTaskRunner queueTaskRunner = new QueueTaskRunner();

    private static final Map<String, TMSEventListener> eventListeners = new HashMap<>();


    public static void addEventListener(TMSEventListener listener) {
        String listenerName = getListenerName(listener);
        eventListeners.put(listenerName, listener);

        //日志输出
        String msg = "[DEBUGGER] TMSEventBus.addEventListener : " + listenerName;
        logger.error(msg);
        System.out.println(msg);
    }


    public static void removeEventListener(TMSEventListener listener) {
        String listenerName = getListenerName(listener);
        eventListeners.remove(listenerName);
    }


    public static void sendEvent(TMSEvent event) {
        if (event == null) {
            return;
        }

        Collection<TMSEventListener> listeners = eventListeners.values();
        for (TMSEventListener listener : listeners) {
            if (listener != null) {
                try {
                    listener.onEvent(event);
                } catch (Throwable e) {
                    //一个事件监听器报错了，让其不影响其他监听器
                    logger.error("", e);
                }
            }
        }
    }


    private static String getListenerName(TMSEventListener listener) {
        String listenerName = listener.getName();
        if (StringUtils.isBlank(listenerName)) {
            listenerName = listener.getClass().getName();
        }
        return listenerName;
    }


    public static void asynSendEvent(final TMSEvent event) {
        if (event == null) {
            return;
        }

        queueTaskRunner.addTask(new QueueTask() {
            @Override
            public void runTask() {
                try {
                    sendEvent(event);
                } catch (Throwable e) {
                    e.printStackTrace();
                    logger.error("", e);
                }
            }
        });
    }


}
