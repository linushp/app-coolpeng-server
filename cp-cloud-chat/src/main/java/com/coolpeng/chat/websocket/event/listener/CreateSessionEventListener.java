package com.coolpeng.chat.websocket.event.listener;

import com.alibaba.fastjson.JSON;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.websocket.WebsocketContainer;
import com.coolpeng.chat.websocket.event.CreateSessionEvent;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
public class CreateSessionEventListener extends TMSEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PeerMsgEventListener.class);

    @Override
    public void onEvent(TMSEvent evt) {
        if (evt instanceof CreateSessionEvent) {
            CreateSessionEvent event = (CreateSessionEvent) evt;
            ChatSessionVO sessionVO = event.getChatSessionVO();
            String json = JSON.toJSONString(event);
            List<String> uidList = sessionVO.getParticipateUidList();
            for (String toUserId : uidList) {
                List<Session>sessions = WebsocketContainer.getSessionByUid(toUserId);
                if (sessions != null) {
                    for(Session session:sessions){
                        try {
                            session.getBasicRemote().sendText(json);
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.error("", e);
                        }
                    }
                }
            }
        }
    }
}
