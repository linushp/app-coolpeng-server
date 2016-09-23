package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.service.api.IChatMsgService;
import com.coolpeng.chat.websocket.event.CreateSessionEvent;
import com.coolpeng.chat.websocket.event.PublicMsgEvent;
import com.coolpeng.framework.cache.CacheManager;
import com.coolpeng.framework.cache.ICache;
import com.coolpeng.framework.event.TMSEvent;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
@Service
public class PublicChatService implements IChatMsgService {

    private static final int MAX_STORE_COUNT = 100;

    private static final String CACHE_KEY = PublicChatService.class.getName();

    @Override
    public TMSEvent saveMessage(UserEntity user, String msg,String msgSummary,String msgId, ChatSessionVO sessionVO){
        String sessionId = sessionVO.getSessionId();
        LinkedList<ChatMsgVO> msgList = getChatMsgList(sessionVO);
        ChatMsgVO chatMsgVO = new ChatMsgVO(user, msg,msgId);
        msgList.add(chatMsgVO);

        if (msgList.size() > MAX_STORE_COUNT) {
            msgList.removeFirst();
        }
        saveGroupChatMsgList(msgList,sessionId);
        return new PublicMsgEvent(chatMsgVO,sessionId,msgSummary);
    }


    @Override
    public LinkedList<ChatMsgVO> getChatMsgList( ChatSessionVO chatSessionVO){
        String sessionId = chatSessionVO.getSessionId();
        ICache cache = CacheManager.getCache();
        Object obj = cache.getData(getCacheKey(sessionId));
        if(obj!=null){
            return ( LinkedList<ChatMsgVO> )obj;
        }
        return new LinkedList<>();
    }

    @Override
    public CreateSessionEvent createSession(UserEntity user, ChatSessionVO sessionVO) {

        //不能创建公共聊天
        return null;
    }


    private void saveGroupChatMsgList(LinkedList<ChatMsgVO> obj,String sessionId){
        ICache cache = CacheManager.getCache();
        cache.putData(getCacheKey(sessionId),obj);
    }

    private String getCacheKey(String sessionId){
        return CACHE_KEY + "---" + sessionId;
    }
}

