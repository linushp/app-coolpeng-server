package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.ChatSessionMessage;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.utils.ChatConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
@Service
public class ChatSessionMessageService {


    public void saveSessionMessage(UserEntity sendMessageUser, ChatSessionVO sessionVO, ChatMsgVO chatMsgVO) throws Exception {
        List<ChatMsgVO> msgList = this.getChatMsgList(sessionVO);


        msgList.add(chatMsgVO);

        //101
        if (msgList.size() > ChatConstant.CHAT_SESSION_MAX_MSG_COUNT) {
            //101 -100
            int begin = msgList.size() - ChatConstant.CHAT_SESSION_MAX_MSG_COUNT;//1
            int end = msgList.size();//101
            msgList = msgList.subList(begin, end);//1,101
        }

        saveChatSessionMessage(sendMessageUser, msgList, sessionVO);
    }

    private void saveChatSessionMessage(UserEntity sendMessageUser, List<ChatMsgVO> msgList, ChatSessionVO sessionVO) throws Exception {
        String sessionId = sessionVO.getSessionId(); //形如：public_1
        ChatSessionMessage entity = ChatSessionMessage.DAO.queryObjectByKV("sessionId", sessionId);
        if (entity == null) {
            entity = new ChatSessionMessage();
            entity.setEntityId(sessionVO.getEntityId());
            entity.setSessionId(sessionVO.getSessionId());
            entity.setSessionType(ChatSessionVO.TYPE_PEER);
            entity.setCreateUserId(sendMessageUser.getId());
        }
        entity.setUpdateUserId(sendMessageUser.getId());
        entity.setChatMsg(msgList);
        ChatSessionMessage.DAO.insertOrUpdate(entity);
    }

    public List<ChatMsgVO> getChatMsgList(ChatSessionVO sessionVO) throws Exception {
        String sessionId = sessionVO.getSessionId(); //形如：public_1
        ChatSessionMessage entity = ChatSessionMessage.DAO.queryObjectByKV("sessionId", sessionId);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getChatMsg();
    }
}
