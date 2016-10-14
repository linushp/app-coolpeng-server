package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.service.UserService;
import com.coolpeng.chat.entity.ChatPeerSession;
import com.coolpeng.chat.entity.ChatRecentSession;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.model.ChatUserVO;
import com.coolpeng.chat.utils.ChatConstant;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.qtask.QueueTask;
import com.coolpeng.framework.qtask.QueueTaskRunner;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.StringUtils;
import com.coolpeng.framework.utils.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Administrator on 2016/9/10.
 */
@Service
public class RecentSessionService {

    private static final Logger logger = LoggerFactory.getLogger(RecentSessionService.class);
    private static final int MAX_RECENT_SIZE = 20;

    private static final QueueTaskRunner queueTaskRunner = new QueueTaskRunner();
    //一般里面只有两个元素
    private static final Map<String, ChatSessionVO> publicChatSessionCache = new HashMap<>();

    @Autowired
    private UserService userService;


    public void asynSaveRecentSession(final String ownerUserId,final ChatSessionVO sessionVO) throws Exception {
        queueTaskRunner.addTask(new QueueTask() {
            @Override
            public void runTask() {
                try {
                    doSaveRecentSession(ownerUserId,sessionVO);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("", e);
                }
            }
        });
    }


    /**
     *
     * @param sessionVO
     * @throws Exception
     */
    public void doSaveRecentSession(String ownerUserId,ChatSessionVO sessionVO) throws Exception {

        if (ChatSessionVO.TYPE_PUBLIC.equalsIgnoreCase(sessionVO.getSessionType())) {
            //如果是public会话，记录一下最后一条消息
            publicChatSessionCache.put(sessionVO.getSessionId(), sessionVO);
        }
        saveUserRecentSession(ownerUserId, sessionVO);
    }


    private void saveUserRecentSession(String ownerUserId, ChatSessionVO sessionVO) throws Exception {
        ChatRecentSession userRecentSession = getUserRecentSession(ownerUserId);
        if (userRecentSession == null) {
            userRecentSession = new ChatRecentSession(ownerUserId);
        }

        List<ChatSessionVO> recentList = userRecentSession.getRecentSessions();
        Tuple tuple = removeElement(recentList, sessionVO.getSessionId());
        recentList = tuple.first();


        if (StringUtils.isBlank(sessionVO.getLastMsgText())){
            ChatSessionVO oldSessionVo = tuple.second();
            if (oldSessionVo!=null){
                sessionVO.setLastMsgText(oldSessionVo.getLastMsgText());
                sessionVO.setLastMsgAvatar(oldSessionVo.getLastMsgAvatar());
                sessionVO.setLastMsgNickname(oldSessionVo.getLastMsgNickname());
                sessionVO.setLastMsgTimeMillis(oldSessionVo.getLastMsgTimeMillis());
                sessionVO.setLastMsgUsername(oldSessionVo.getLastMsgUsername());
                sessionVO.setLastMsgUid(oldSessionVo.getLastMsgUid());
            }
        }

        recentList.add(sessionVO);

        if (recentList.size() > MAX_RECENT_SIZE) {
            LinkedList<ChatSessionVO> linkedList = new LinkedList(recentList);
            linkedList.removeFirst();
            recentList = linkedList;
        }

        userRecentSession.setRecentSessions(recentList);
        ChatRecentSession.DAO.insertOrUpdate(userRecentSession);
    }

    private Tuple removeElement(List<ChatSessionVO> recentList, String sessionId) {
        List<ChatSessionVO> result = new ArrayList<>();

        ChatSessionVO chatSessionVO = null;
        for (ChatSessionVO s : recentList) {
            if (!sessionId.equals(s.getSessionId())) {
                result.add(s);
            }else {
                chatSessionVO = s;
            }
        }

        return new Tuple(result,chatSessionVO);
    }


    private ChatRecentSession getUserRecentSession(String uid) throws FieldNotFoundException {
        return ChatRecentSession.DAO.queryObjectByKV("ownerUid", uid);
    }


    public List<ChatSessionVO> getRecentChatSessionVOList(UserEntity user) throws Exception {
        ChatRecentSession userRecentSession = getUserRecentSession(user.getId());
        if (userRecentSession == null) {
            userRecentSession = new ChatRecentSession(user.getId());
        }

        List<ChatSessionVO> recentSessions = userRecentSession.getRecentSessions();
        ChatSessionVO m0 = new ChatSessionVO(ChatSessionVO.TYPE_ROBOT, "1", ChatConstant.UBIBI_ROBOT_USER.getNickname(), ChatConstant.UBIBI_ROBOT_ICON);
        ChatSessionVO m1 = new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC, "1", "所有人",ChatConstant.PUBLIC_CHANNEL_ICON2);
//        ChatSessionVO m2 = new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC, "2", "技术灌水",ChatConstant.PUBLIC_CHANNEL_ICON2);


        if (!recentSessions.contains(m0)) {
            m0.setLastMsgText(ChatConstant.UBIBI_ROBOT_HELLOWORLD);
            recentSessions.add(m0);
        }

        if (!recentSessions.contains(m1)) {
            recentSessions.add(m1);
        }

        //对于public的特殊处理，从缓存中获取lastMessage
        addonPublicRecentMessage(recentSessions);
        //对于peer的特殊处理，打补丁修复头像，昵称的更改
        resetPeerIconAndTitle(recentSessions,user);
        return sortByLastMsgTimeMillis(recentSessions);

    }


    private void resetPeerIconAndTitle(List<ChatSessionVO> recentSessions,UserEntity currentLoginUser) throws Exception{


        String currentUid = currentLoginUser.getId();

        List<String> anotherUserIdList = new ArrayList<>();
        for (ChatSessionVO sessionVO:recentSessions){
            if (ChatSessionVO.TYPE_PEER.equals(sessionVO.getSessionType())){
                List<String> pUidList = sessionVO.getParticipateUidList();
                if (!CollectionUtil.isEmpty(pUidList)){
                    for (String pUid :pUidList){
                        if (!currentUid.equals(pUid)){
                            anotherUserIdList.add(pUid);
                        }
                    }
                }
            }
        }

        if (CollectionUtil.isEmpty(anotherUserIdList)){
            return;
        }

        List<UserEntity> userList = userService.getUserEntityListByUidList(anotherUserIdList);

        if (CollectionUtil.isEmpty(userList)){
            return;
        }

        Map<String,UserEntity> userMap = CollectionUtil.toMap(userList,"id");

        for (ChatSessionVO sessionVO:recentSessions){
            if (ChatSessionVO.TYPE_PEER.equals(sessionVO.getSessionType())){
                List<String> pUidList = sessionVO.getParticipateUidList();
                if (!CollectionUtil.isEmpty(pUidList)){
                    for (String pUid :pUidList){
                        if (!currentUid.equals(pUid)){
                            UserEntity anotherUser = userMap.get(pUid);
                            if (anotherUser!=null){
                                sessionVO.setSessionIcon(anotherUser.getAvatar());
                                sessionVO.setSessionTitle("与 "+ anotherUser.getNickname() + " 对话");
                            }
                        }
                    }
                }
            }
        }

    }


    //如果是public session
    private void addonPublicRecentMessage(List<ChatSessionVO> recentSessions) {
        for (ChatSessionVO sessionVO : recentSessions) {
            if (ChatSessionVO.TYPE_PUBLIC.equalsIgnoreCase(sessionVO.getSessionType())) {

                String sessionId = sessionVO.getSessionId();
                ChatSessionVO m = publicChatSessionCache.get(sessionId);

                if (m != null) {
                    sessionVO.setLastMsgText(m.getLastMsgText());
                    sessionVO.setLastMsgAvatar(m.getLastMsgAvatar());
                    sessionVO.setLastMsgNickname(m.getLastMsgNickname());
                    sessionVO.setLastMsgTimeMillis(m.getLastMsgTimeMillis());
                    sessionVO.setLastMsgUsername(m.getLastMsgUsername());
                    sessionVO.setLastMsgUid(m.getLastMsgUid());
                }
            }

        }
    }


    private List<ChatSessionVO> sortByLastMsgTimeMillis(List<ChatSessionVO> recentSessions) {
        Collections.sort(recentSessions, new Comparator<ChatSessionVO>() {
            @Override
            public int compare(ChatSessionVO o1, ChatSessionVO o2) {
                long m = o2.getLastMsgTimeMillis() - o1.getLastMsgTimeMillis();
                return (int) m;
            }
        });
        return recentSessions;
    }


    public void deleteRecentSession(String ownerUserId, String sessionId) throws Exception {


        ChatRecentSession userRecentSession = getUserRecentSession(ownerUserId);
        if (userRecentSession == null) {
            userRecentSession = new ChatRecentSession(ownerUserId);
        }

        List<ChatSessionVO> recentList = userRecentSession.getRecentSessions();
        Tuple tuple = removeElement(recentList, sessionId);
        recentList = tuple.first();

        userRecentSession.setRecentSessions(recentList);
        ChatRecentSession.DAO.insertOrUpdate(userRecentSession);
    }


}
