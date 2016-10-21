package com.coolpeng.chat.service.api;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.websocket.event.CreateSessionEvent;
import com.coolpeng.framework.event.TMSEvent;

import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
public interface IChatMsgService {


    TMSEvent saveMessage(UserEntity user, String msg, String msgSummary, String msgId, ChatSessionVO sessionVO,boolean isRefreshRecentSession,String messageType) throws Exception;

    List<ChatMsgVO> getChatMsgList(ChatSessionVO sessionVO) throws Exception;

    CreateSessionEvent createSession(UserEntity user, ChatSessionVO sessionVO) throws Exception;

    //只有peer会话可以删除
    void deleteSession(UserEntity currentLoginUser, String sessionId) throws Exception;
}
