package com.coolpeng.chat.websocket.event;

import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.framework.event.TMSEvent;

/**
 * Created by Administrator on 2016/9/10.
 */
public class PublicMsgEvent extends TMSEvent {
    private ChatMsgVO chatMsgVO;
    private String sessionId;

    public PublicMsgEvent(ChatMsgVO chatMsgVO,String sessionId) {
        this.chatMsgVO = chatMsgVO;
        this.sessionId = sessionId;
        this.setName(this.getClass().getSimpleName());
    }

    public ChatMsgVO getChatMsgVO() {
        return chatMsgVO;
    }

    public void setChatMsgVO(ChatMsgVO chatMsgVO) {
        this.chatMsgVO = chatMsgVO;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
