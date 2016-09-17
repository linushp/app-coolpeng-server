package com.coolpeng.chat.websocket.event.listener;

import com.alibaba.fastjson.JSON;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.websocket.WebsocketContainer;
import com.coolpeng.chat.websocket.event.PeerMsgEvent;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by Administrator on 2016/9/15.
 */
public class PeerMsgEventListener extends TMSEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PeerMsgEventListener.class);

    @Override
    public void onEvent(TMSEvent event) {
        if (event instanceof PeerMsgEvent){
            PeerMsgEvent event1 =(PeerMsgEvent)event ;
            String toUserId = event1.getReceiveUserId();
            Session session = WebsocketContainer.getSessionByUid(toUserId);
            if (session!=null){
                try {
                    String json = JSON.toJSONString(event1);
                    session.getBasicRemote().sendText(json);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("",e);
                }
            }
        }
    }
}
