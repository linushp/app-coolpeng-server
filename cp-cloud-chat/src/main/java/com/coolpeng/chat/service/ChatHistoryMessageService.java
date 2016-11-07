//package com.coolpeng.chat.service;
//
//import com.coolpeng.chat.entity.ChatHistoryMessage;
//import com.coolpeng.chat.model.ChatMsgVO;
//import com.coolpeng.framework.cache.CacheManager;
//import com.coolpeng.framework.cache.ICache;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * 历史消息
// */
//@Service
//public class ChatHistoryMessageService {
//
//    private static final String CACHE_KEY = ChatHistoryMessageService.class.getName();
//
//
//    public void saveHistoryMessageIfNecessary(List<ChatMsgVO> msgList, String sessionId) {
//        int index = getCachedIndex(sessionId);
//        if (index>=100){
//            ChatHistoryMessage entity = new ChatHistoryMessage(sessionId,msgList);
//            ChatHistoryMessage.DAO.insert(entity);
//            index=0;
//        }
//        else {
//            index++;
//        }
//        setCachedIndex(sessionId,index);
//    }
//
//
//    public int getCachedIndex(String sessionId) {
//        Map<String, Integer> obj = getCachedIndexMap();
//        Integer cacheIndex = obj.get(sessionId);
//        if (cacheIndex == null) {
//            cacheIndex = 0;
//            obj.put(sessionId, cacheIndex);
//        }
//        return cacheIndex;
//    }
//
//
//    public void setCachedIndex(String sessionId, int cacheIndex) {
//        Map<String, Integer> obj = getCachedIndexMap();
//        obj.put(sessionId, cacheIndex);
//    }
//
//
//    //TODO 暂时由于现在使用的是内存cache,可以这样做。
//    public Map<String, Integer> getCachedIndexMap() {
//        ICache cache = CacheManager.getCache();
//        Map<String, Integer> obj = (Map<String, Integer>) cache.getData(CACHE_KEY);
//        if (obj == null) {
//            obj = new HashMap<>();
//            cache.putData(CACHE_KEY, obj);
//        }
//        return obj;
//    }
//
//
//}
