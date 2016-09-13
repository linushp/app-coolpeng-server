package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.UserRecentSession;
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

    public List<ChatSessionVO> sortSessionByRecent(List<ChatSessionVO> sessionVOList, UserEntity user) throws FieldNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UserRecentSession userRecentSession = getUserRecentSession(user.getId());
        if (userRecentSession == null) {
            return sessionVOList;
        }

        List<String> recentIdList = userRecentSession.getRecentIdList();
        Map<String, ChatSessionVO> map = CollectionUtil.toMap(sessionVOList, "sessionId");
        Set<String> hashSet = new HashSet<>();
        List<ChatSessionVO> result = new ArrayList<>();
        for (String sessionId : recentIdList) {
            ChatSessionVO vo = map.get(sessionId);
            if (vo != null) {
                result.add(vo);
                hashSet.add(sessionId);
            }
        }

        for (ChatSessionVO sessionVO : sessionVOList) {
            if (!hashSet.contains(sessionVO.getSessionId())) {
                result.add(sessionVO);
            }
        }
        return result;

    }

    public void saveRecentSession(String sessionId, UserEntity user) throws FieldNotFoundException, UpdateErrorException {
        UserRecentSession userRecentSession = getUserRecentSession(user.getId());
        if (userRecentSession == null) {
            userRecentSession = new UserRecentSession(user.getId());
        }

        List<String> recentList = userRecentSession.getRecentIdList();
        recentList.add(sessionId);

        if (recentList.size()> MAX_RECENT_SIZE){
            LinkedList<String> linkedList = new LinkedList(recentList);
            linkedList.removeFirst();
            recentList = linkedList;
        }

        userRecentSession.setRecentIdList(recentList);
        UserRecentSession.DAO.insertOrUpdate(userRecentSession);
    }


    private UserRecentSession getUserRecentSession(String uid) throws FieldNotFoundException {
        return UserRecentSession.DAO.findObjectBy("ownerUid",uid);
    }
}
