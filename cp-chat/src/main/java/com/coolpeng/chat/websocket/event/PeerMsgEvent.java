package com.coolpeng.chat.websocket.event;

import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.framework.event.TMSEvent;

/**
 * Created by Administrator on 2016/9/15.
 */
public class PeerMsgEvent extends TMSEvent  {

    private ChatMsgVO chatMsgVO;

    private String receiveUserId;

    public PeerMsgEvent(ChatMsgVO chatMsgVO, String receiveUserId) {
        this.chatMsgVO = chatMsgVO;
        this.receiveUserId = receiveUserId;
        this.setName(this.getClass().getSimpleName());
    }

    public ChatMsgVO getChatMsgVO() {
        return chatMsgVO;
    }

    public void setChatMsgVO(ChatMsgVO chatMsgVO) {
        this.chatMsgVO = chatMsgVO;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}
