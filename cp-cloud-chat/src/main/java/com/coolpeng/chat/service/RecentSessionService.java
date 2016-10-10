package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.ChatRecentSession;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.utils.ChatConstant;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.qtask.QueueTask;
import com.coolpeng.framework.qtask.QueueTaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/9/10.
 */
@Service
public class RecentSessionService {

    private static final Logger logger = LoggerFactory.getLogger(RecentSessionService.class);
    private static final int MAX_RECENT_SIZE = 20;

    private static QueueTaskRunner queueTaskRunner = new QueueTaskRunner();

    public void saveRecentSession(final ChatSessionVO sessionVO, final UserEntity user,final String msgSummary) throws Exception {
        queueTaskRunner.addTask(new QueueTask() {
            @Override
            public void runTask() {
                try {
                    doSaveRecentSession(sessionVO,user,msgSummary);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("",e);
                }
            }
        });
    }

    private void doSaveRecentSession(ChatSessionVO sessionVO, UserEntity user,String msgSummary) throws Exception {
        ChatRecentSession userRecentSession = getUserRecentSession(user.getId());
        if (userRecentSession == null) {
            userRecentSession = new ChatRecentSession(user.getId());
        }

        List<ChatSessionVO> recentList = userRecentSession.getRecentSessions();
        recentList = removeElement(recentList, sessionVO);

        sessionVO.setLastMsgText(msgSummary);
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
        return ChatRecentSession.DAO.queryObjectByKV("ownerUid", uid);
    }


    public List<ChatSessionVO> getRecentChatSessionVOList(UserEntity user) throws FieldNotFoundException {
        ChatRecentSession userRecentSession = getUserRecentSession(user.getId());
        if (userRecentSession == null) {
            userRecentSession = new ChatRecentSession(user.getId());
        }

        List<ChatSessionVO> recentSessions = userRecentSession.getRecentSessions();
        ChatSessionVO m0 = new ChatSessionVO(ChatSessionVO.TYPE_ROBOT, "1", ChatConstant.UBIBI_ROBOT_USER.getNickname(), ChatConstant.UBIBI_ROBOT_ICON);
        ChatSessionVO m1 = new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC, "1", "公共频道");
        ChatSessionVO m2 = new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC, "2", "技术灌水");


        if (!recentSessions.contains(m0)){
            recentSessions.add(m0);
        }

        if (!recentSessions.contains(m1)){
            recentSessions.add(m1);
        }

        if (!recentSessions.contains(m2)){
            recentSessions.add(m2);
        }

        return sortByLastMsgTimeMillis(recentSessions);

    }



    private List<ChatSessionVO> sortByLastMsgTimeMillis(List<ChatSessionVO> recentSessions) {
        Collections.sort(recentSessions, new Comparator<ChatSessionVO>() {
            @Override
            public int compare(ChatSessionVO o1, ChatSessionVO o2) {
                long m = o2.getLastMsgTimeMillis() - o1.getLastMsgTimeMillis();
                return (int)m;
            }
        });
        return recentSessions;
    }

}
