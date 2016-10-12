package com.coolpeng.chat.websocket.event.init;

import com.coolpeng.chat.websocket.event.listener.CreateSessionEventListener;
import com.coolpeng.chat.websocket.event.listener.PeerMsgEventListener;
import com.coolpeng.chat.websocket.event.listener.PublicMsgEventListener;
import com.coolpeng.framework.event.TMSEventBus;

/**
 * Created by Administrator on 2016/9/15.
 */
public class EventListenerInit {

    private static boolean isInited = false;

    public static void init() {
        if (!isInited) {
            TMSEventBus.addEventListener(new PublicMsgEventListener());
            TMSEventBus.addEventListener(new PeerMsgEventListener());
            TMSEventBus.addEventListener(new CreateSessionEventListener());
        }
        isInited = true;
    }
}
