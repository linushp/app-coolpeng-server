package com.coolpeng.chat.websocket;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatUserVO;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;

import javax.websocket.Session;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/9/15.
 */
public class WebsocketContainer {
    private static final Map<String, Session> sessionId2Session = new ConcurrentHashMap<>();
    private static final Map<String, Session> userId2Session = new ConcurrentHashMap<>();
    private static final Map<String, ChatUserVO> userId2UserVO = new ConcurrentHashMap<>();

    public static void onOpen(Session session) throws ParameterErrorException, FieldNotFoundException {

        Map<String, List<String>> map = session.getRequestParameterMap();
        List<String> uidList = map.get("uid");
        String uid = uidList.get(0);
        userId2Session.put(uid, session);
        sessionId2Session.put(session.getId(), session);
        UserEntity userEntity = UserEntity.DAO.queryById(uid);
        userId2UserVO.put(uid, new ChatUserVO(userEntity));
    }

    public static void onClose(Session session) {
        Map<String, List<String>> map = session.getRequestParameterMap();
        List<String> uidList = map.get("uid");
        String uid = uidList.get(0);

        userId2Session.remove(uid);
        userId2UserVO.remove(uid);
        sessionId2Session.remove(session.getId());
    }

    public static Collection<Session> getAllOnlineSession() {
        return userId2Session.values();
    }

    public static Session getSessionByUid(String uid) {
        return userId2Session.get(uid);
    }

    public static Collection<ChatUserVO> getAllOnlineUserVO(){
        return userId2UserVO.values();
    }
}
