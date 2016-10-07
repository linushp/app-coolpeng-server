package com.coolpeng.chat.websocket.event.listener;

import com.alibaba.fastjson.JSON;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.websocket.WebsocketContainer;
import com.coolpeng.chat.websocket.event.PublicMsgEvent;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventListener;
import com.coolpeng.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
public class PublicMsgEventListener extends TMSEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PublicMsgEventListener.class);

    @Override
    public void onEvent(TMSEvent event) {
        if (event instanceof PublicMsgEvent){
            List<Session> sessions = WebsocketContainer.getAllOnlineSession();
            if(!CollectionUtil.isEmpty(sessions)){
                PublicMsgEvent publicMsgEvent = (PublicMsgEvent)event;
                String json = JSON.toJSONString(publicMsgEvent);
                for (Session session : sessions){
                    try {
                        session.getBasicRemote().sendText(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error("",e);
                    }
                }
            }
        }
    }
}
