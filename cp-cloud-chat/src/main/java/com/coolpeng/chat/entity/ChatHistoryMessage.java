//package com.coolpeng.chat.entity;
//
//import com.coolpeng.chat.model.ChatMsgVO;
//import com.coolpeng.framework.db.BaseEntity;
//import com.coolpeng.framework.db.SimpleDAO;
//import com.coolpeng.framework.db.annotation.FieldDef;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 历史消息
// *
// * CREATE TABLE t_chat_history_message
// (
// id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
// create_time VARCHAR(256),
// update_time VARCHAR(256),
// create_user_id VARCHAR(256),
// update_user_id VARCHAR(256),
// status INT DEFAULT 0,
// session_id VARCHAR(256),
// chat_msg LONGTEXT
// );
//
// */
//public class ChatHistoryMessage extends BaseEntity {
//    public static final SimpleDAO<ChatHistoryMessage> DAO = new SimpleDAO(ChatHistoryMessage.class);
//
//
//    private String sessionId; //形如：“public_1”,"peer_1"
//
//    @FieldDef(jsonColumn = {List.class, ChatMsgVO.class})
//    private List<ChatMsgVO> chatMsg = new ArrayList<>();
//
//
//    public ChatHistoryMessage(String sessionId, List<ChatMsgVO> chatMsg) {
//        this.sessionId = sessionId;
//        this.chatMsg = chatMsg;
//    }
//
//    public String getSessionId() {
//        return sessionId;
//    }
//
//    public void setSessionId(String sessionId) {
//        this.sessionId = sessionId;
//    }
//
//    public List<ChatMsgVO> getChatMsg() {
//        return chatMsg;
//    }
//
//    public void setChatMsg(List<ChatMsgVO> chatMsg) {
//        this.chatMsg = chatMsg;
//    }
//}