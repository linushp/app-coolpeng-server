package com.coolpeng.chat.websocket.event;

import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.framework.event.TMSEvent;

/**
 * Created by Administrator on 2016/9/15.
 */
public class CreateSessionEvent extends TMSEvent {
    private ChatSessionVO chatSessionVO;

    public CreateSessionEvent(ChatSessionVO chatSessionVO) {
        this.chatSessionVO = chatSessionVO;
        this.setName(this.getClass().getSimpleName());
    }

    public ChatSessionVO getChatSessionVO() {
        return chatSessionVO;
    }

    public void setChatSessionVO(ChatSessionVO chatSessionVO) {
        this.chatSessionVO = chatSessionVO;
    }
}
