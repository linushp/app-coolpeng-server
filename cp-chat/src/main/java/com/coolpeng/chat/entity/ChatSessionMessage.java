package com.coolpeng.chat.entity;

import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.framework.db.BaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
public class ChatSessionMessage extends BaseEntity {

    public static final SimpleDAO<ChatSessionMessage> DAO = new SimpleDAO(ChatSessionMessage.class);

    private String sessionType; //peer,public

    private String entityId;

    private String sessionId; //形如：“public_1”,"peer_1"

    @FieldDef(jsonColumn = {List.class, ChatMsgVO.class})
    private List<ChatMsgVO> chatMsg = new ArrayList<>();


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public List<ChatMsgVO> getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(List<ChatMsgVO> chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
