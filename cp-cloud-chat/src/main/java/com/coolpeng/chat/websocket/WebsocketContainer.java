package com.coolpeng.chat.websocket;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatUserVO;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.utils.CollectionUtil;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/9/15.
 */
public class WebsocketContainer {
    private static final Map<String, Session> connectionId2Session = new ConcurrentHashMap<>();
    private static final Map<String, List<Session>> userId2Session = new ConcurrentHashMap<>();
    private static final Map<String, ChatUserVO> userId2UserVO = new ConcurrentHashMap<>();

    public static void onOpen(Session session) throws Exception {
        String uid = getUid(session);
        String connectionId = getConnectionId(session);

        UserEntity userEntity = UserEntity.DAO.queryById(uid);

        getSessionByUid(uid).add(session);
        connectionId2Session.put(connectionId, session);
        userId2UserVO.put(uid, new ChatUserVO(userEntity));

        System.out.println("WebSocket Connected , uid = " + uid + ", current online userCount = " + connectionId2Session.size());
    }

    public static void onClose(Session session) {

        String uid = getUid(session);
        String connectionId = getConnectionId(session);
        removeBySessionIdFromUserId2Session(uid, session.getId());
        userId2UserVO.remove(uid);
        connectionId2Session.remove(connectionId);
    }


    private static void removeBySessionIdFromUserId2Session(String uid,String sessionId){
        List<Session> sessions = userId2Session.get(uid);
        if (CollectionUtil.isEmpty(sessions)){
            return;
        }

        List<Session> result = new ArrayList<>();
        for (Session session:result){
            if (!sessionId.equals(session.getId())){
                result.add(session);
            }
        }

        if (result.isEmpty()){
            userId2Session.put(uid,null);
        }else {
            userId2Session.put(uid,result);
        }
    }


    private static String getUid(Session session) {
        Map<String, List<String>> map = session.getRequestParameterMap();
        List<String> uidList = map.get("uid");
        String uid = uidList.get(0);
        return uid;
    }

    private static String getConnectionId(Session session) {
        Map<String, List<String>> map = session.getRequestParameterMap();
        List<String> uidList = map.get("connectionId");
        String uid = uidList.get(0);
        return uid;
    }


    public static Collection<Session> getAllOnlineSession() {
        return connectionId2Session.values();
    }

    public static List<Session> getSessionByUid(String uid) {
        List<Session> sessions = userId2Session.get(uid);
        if (sessions == null) {
            sessions = new ArrayList<>();
            userId2Session.put(uid, sessions);
        }
        return sessions;
    }

    public static Collection<ChatUserVO> getAllOnlineUserVO() {
        return userId2UserVO.values();
    }
}
