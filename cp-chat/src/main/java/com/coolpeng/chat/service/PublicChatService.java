package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.framework.cache.CacheManager;
import com.coolpeng.framework.cache.ICache;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/9/10.
 */
@Service
public class PublicChatService {

    private static final int MAX_STORE_COUNT = 100;

    private static final String CACHE_KEY = PublicChatService.class.getName();

    public ChatMsgVO sendPublicMessage(UserEntity user,String msg,String sessionId){
        LinkedList<ChatMsgVO> msgList = getPublicChatMsgList(sessionId);
        ChatMsgVO chatMsgVO = new ChatMsgVO(user, msg);
        msgList.add(chatMsgVO);

        if (msgList.size() > MAX_STORE_COUNT) {
            msgList.removeFirst();
        }
        saveGroupChatMsgList(msgList,sessionId);

        return chatMsgVO;
    }


    public LinkedList<ChatMsgVO> getPublicChatMsgList(String sessionId){
        ICache cache = CacheManager.getCache();
        Object obj = cache.getData(getCacheKey(sessionId));
        if(obj!=null){
            return ( LinkedList<ChatMsgVO> )obj;
        }
        return new LinkedList<>();
    }



    private void saveGroupChatMsgList(LinkedList<ChatMsgVO> obj,String sessionId){
        ICache cache = CacheManager.getCache();
        cache.putData(getCacheKey(sessionId),obj);
    }

    private String getCacheKey(String sessionId){
        return CACHE_KEY + "---" + sessionId;
    }
}

