package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.ChatRecentSession;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.CollectionUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Administrator on 2016/9/10.
 */
@Service
public class RecentSessionService {

    private static final int MAX_RECENT_SIZE = 20;


    public void saveRecentSession(ChatSessionVO sessionVO, UserEntity user,String msg) throws FieldNotFoundException, UpdateErrorException {
        ChatRecentSession userRecentSession = getUserRecentSession(user.getId());
        if (userRecentSession == null) {
            userRecentSession = new ChatRecentSession(user.getId());
        }

        List<ChatSessionVO> recentList = userRecentSession.getRecentSessions();
        recentList = removeElement(recentList, sessionVO);

        sessionVO.setLastMsgText(msg);
        sessionVO.setLastMsgAvatar(user.getAvatar());
        sessionVO.setLastMsgNickname(user.getNickname());
        sessionVO.setLastMsgTimeMillis(System.currentTimeMillis());
        sessionVO.setLastMsgUsername(user.getUsername());
        sessionVO.setLastMsgUid(user.getId());

        recentList.add(sessionVO);

        if (recentList.size()> MAX_RECENT_SIZE){
            LinkedList<ChatSessionVO> linkedList = new LinkedList(recentList);
            linkedList.removeFirst();
            recentList = linkedList;
        }

        userRecentSession.setRecentSessions(recentList);
        ChatRecentSession.DAO.insertOrUpdate(userRecentSession);
    }

    private List<ChatSessionVO> removeElement( List<ChatSessionVO> recentList, ChatSessionVO sessionId) {
        List<ChatSessionVO> result = new ArrayList<>();
        for (ChatSessionVO s:recentList){
            if(!sessionId.equals(s)){
                result.add(s);
            }
        }
        return result;
    }


    private ChatRecentSession getUserRecentSession(String uid) throws FieldNotFoundException {
        return ChatRecentSession.DAO.findObjectBy("ownerUid",uid);
    }


    public List<ChatSessionVO> getRecentChatSessionVOList(UserEntity user) throws FieldNotFoundException {
        ChatRecentSession userRecentSession = getUserRecentSession(user.getId());
        if (userRecentSession == null) {
            userRecentSession = new ChatRecentSession(user.getId());
        }

        List<ChatSessionVO> recentSessions = userRecentSession.getRecentSessions();
        ChatSessionVO m1 = new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC, "1", "公共频道");
        ChatSessionVO m2 = new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC, "2", "技术灌水");

        if (!recentSessions.contains(m1)){
            recentSessions.add(m1);
        }

        if (!recentSessions.contains(m2)){
            recentSessions.add(m2);
        }

        return recentSessions;

    }
}
