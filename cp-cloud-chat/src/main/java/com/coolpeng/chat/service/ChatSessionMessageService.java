package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.ChatSessionMessage;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.UpdateErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
@Service
public class ChatSessionMessageService {


    private static final int MAX_STORE_MSG_COUNT = 100;

    public void saveSessionMessage(UserEntity user, ChatSessionVO sessionVO, ChatMsgVO chatMsgVO) throws Exception {
        List<ChatMsgVO> msgList = this.getChatMsgList(sessionVO);
        msgList.add(chatMsgVO);

        if (msgList.size() > MAX_STORE_MSG_COUNT) {
            //101 -100
            int begin = msgList.size() - MAX_STORE_MSG_COUNT;//1
            int end = msgList.size();//100
            msgList = msgList.subList(begin, end + 1);
        }

        saveChatSessionMessage(user, msgList, sessionVO);
    }

    private void saveChatSessionMessage(UserEntity user, List<ChatMsgVO> msgList, ChatSessionVO sessionVO) throws Exception {
        String sessionId = sessionVO.getSessionId(); //形如：public_1
        ChatSessionMessage entity = ChatSessionMessage.DAO.findObjectBy("sessionId", sessionId);
        if (entity == null) {
            entity = new ChatSessionMessage();
            entity.setEntityId(sessionVO.getEntityId());
            entity.setSessionId(sessionVO.getSessionId());
            entity.setSessionType(ChatSessionVO.TYPE_PEER);
            entity.setCreateUserId(user.getId());
        }
        entity.setUpdateUserId(user.getId());
        entity.setChatMsg(msgList);
        ChatSessionMessage.DAO.insertOrUpdate(entity);
    }

    public List<ChatMsgVO> getChatMsgList(ChatSessionVO sessionVO) throws Exception {
        String sessionId = sessionVO.getSessionId(); //形如：public_1
        ChatSessionMessage entity = ChatSessionMessage.DAO.findObjectBy("sessionId", sessionId);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getChatMsg();
    }
}
