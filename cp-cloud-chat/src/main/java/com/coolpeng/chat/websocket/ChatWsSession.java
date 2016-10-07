package com.coolpeng.chat.websocket;

import com.coolpeng.chat.model.ChatUserVO;

import javax.websocket.Session;

/**
 * Created by Administrator on 2016/10/7.
 */
public class ChatWsSession {
    private Session session;
    private ChatUserVO chatUserVO;

    public ChatWsSession() {
    }

    public ChatWsSession(Session session, ChatUserVO chatUserVO) {
        this.session = session;
        this.chatUserVO = chatUserVO;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ChatUserVO getChatUserVO() {
        return chatUserVO;
    }

    public void setChatUserVO(ChatUserVO chatUserVO) {
        this.chatUserVO = chatUserVO;
    }
}
