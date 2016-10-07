package com.coolpeng.chat.websocket;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/9/15.
 */
public class WebsocketContainer {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketContainer.class);
    private static final Map<String, ChatWsSession> currentConnections = new ConcurrentHashMap<>();

    public static void onOpen(Session session) throws Exception {
        String uid = getUid(session);
        String connectionId = getConnectionId(session);
        UserEntity userEntity = UserEntity.DAO.queryById(uid);
        if (userEntity != null) {
            currentConnections.put(connectionId, new ChatWsSession(session, new ChatUserVO(userEntity)));
        }
        logger.info("WebSocket Connected , uid = " + uid + ", current online userCount = " + currentConnections.size());
    }

    public static void onClose(Session session) {
        String uid = getUid(session);
        String connectionId = getConnectionId(session);
        currentConnections.remove(connectionId);
        logger.info("WebSocket Closed , uid = " + uid + ", current online userCount = " + currentConnections.size());
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


    public static List<Session> getAllOnlineSession() {
        List<Session> sessions = new ArrayList<>();
        Collection<ChatWsSession> values = currentConnections.values();
        for (ChatWsSession ws : values) {
            sessions.add(ws.getSession());
        }
        return sessions;
    }

    public static List<Session> getSessionByUid(String uid) {
        List<Session> sessions = new ArrayList<>();
        Collection<ChatWsSession> values = currentConnections.values();
        for (ChatWsSession ws : values) {
            if (uid.equals(ws.getChatUserVO().getUid())) {
                sessions.add(ws.getSession());
            }
        }
        return sessions;
    }

    public static Collection<ChatUserVO> getAllOnlineUserVO() {
        Set<ChatUserVO> userVOList = new HashSet<>();
        Collection<ChatWsSession> values = currentConnections.values();
        for (ChatWsSession ws : values) {
            userVOList.add(ws.getChatUserVO());
        }
        return userVOList;
    }
}
