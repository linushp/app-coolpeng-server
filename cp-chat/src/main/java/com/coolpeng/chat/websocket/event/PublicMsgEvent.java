package com.coolpeng.chat.websocket.event;

import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.framework.event.TMSEvent;

/**
 * Created by Administrator on 2016/9/10.
 */
public class PublicMsgEvent extends TMSEvent {
    private ChatMsgVO chatMsgVO;

    public PublicMsgEvent(ChatMsgVO chatMsgVO) {
        this.chatMsgVO = chatMsgVO;
        this.setData(chatMsgVO);
    }

    public PublicMsgEvent(String name, Object data, ChatMsgVO chatMsgVO) {
        super(name, data);
        this.chatMsgVO = chatMsgVO;
    }

    public ChatMsgVO getChatMsgVO() {
        return chatMsgVO;
    }

    public void setChatMsgVO(ChatMsgVO chatMsgVO) {
        this.chatMsgVO = chatMsgVO;
    }
}
