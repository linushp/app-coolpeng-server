package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.ChatPeerSession;
import com.coolpeng.chat.entity.ChatSessionMessage;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.service.api.IChatMsgService;
import com.coolpeng.chat.websocket.event.CreateSessionEvent;
import com.coolpeng.chat.websocket.event.PeerMsgEvent;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/9/15.
 */
@Service
public class PeerChatService implements IChatMsgService {

    @Autowired
    private ChatSessionMessageService chatSessionMessageService;
    @Autowired
    private RecentSessionService recentSessionService;

    @Override
    public TMSEvent saveMessage(UserEntity user, String msg,ChatSessionVO sessionVO) throws Exception {
        String entityId = sessionVO.getEntityId();
        ChatPeerSession chatSessionInfo = ChatPeerSession.DAO.findObjectBy("id", entityId);
        ChatMsgVO chatMsgVO = new ChatMsgVO(user, msg);
        String currentUid = user.getId();
        String receiveUserId = null;
        if (currentUid.equals(chatSessionInfo.getPeer1Uid())) {
            receiveUserId = chatSessionInfo.getPeer2Uid();
        } else {
            receiveUserId = chatSessionInfo.getPeer1Uid();
        }

        chatSessionMessageService.saveSessionMessage(user,sessionVO,chatMsgVO);
        return new PeerMsgEvent(chatMsgVO, receiveUserId);
    }

    @Override
    public List<ChatMsgVO> getChatMsgList(ChatSessionVO sessionVO) throws Exception {
        return chatSessionMessageService.getChatMsgList(sessionVO);
    }


    @Override
    public CreateSessionEvent createSession(UserEntity user, ChatSessionVO tempSessionVO) throws Exception {

        List<String> participateUidList = tempSessionVO.getParticipateUidList();

        String uid1 = participateUidList.get(0);
        String uid2 = participateUidList.get(1);
        UserEntity user1 = UserEntity.DAO.queryById(uid1);
        UserEntity user2 = UserEntity.DAO.queryById(uid2);
        ChatPeerSession chatPeerSession = getOrCreateChatPeerSession(user1, user2);

        ChatSessionVO sessionVO1 = toChatSessionVO(chatPeerSession, user1, user2);
        ChatSessionVO sessionVO2 = toChatSessionVO(chatPeerSession, user2, user1);
        recentSessionService.saveRecentSession(sessionVO1, user1,"","");
        recentSessionService.saveRecentSession(sessionVO2, user2,"","");


        sessionVO1.setParticipateUidList(tempSessionVO.getParticipateUidList());
        return new CreateSessionEvent(sessionVO1);
    }


    private ChatSessionVO toChatSessionVO(ChatPeerSession chatPeerSession, UserEntity ownerUser,UserEntity anotherUser) {
        ChatSessionVO vo = new ChatSessionVO(ChatSessionVO.TYPE_PEER,chatPeerSession.getId(), "与 " + anotherUser.getNickname() +" 对话") ;
        vo.setSessionIcon(anotherUser.getAvatar());
        return vo;
    }


    private ChatPeerSession getOrCreateChatPeerSession(UserEntity user1,UserEntity user2) throws Exception{

        String uid1= user1.getId();
        String uid2= user2.getId();

        ChatPeerSession chatPeerSession = ChatPeerSession.DAO.findObjectBy("peer1Uid", uid1, "peer2Uid", uid2);
        if (chatPeerSession == null) {
            chatPeerSession = ChatPeerSession.DAO.findObjectBy("peer2Uid", uid1, "peer1Uid", uid2);
        }

        if (chatPeerSession == null) {
            chatPeerSession = new ChatPeerSession();
            chatPeerSession.setPeer1Uid(user1.getId());
            chatPeerSession.setPeer1Avatar(user1.getAvatar());
            chatPeerSession.setPeer1Nickname(user1.getNickname());
            chatPeerSession.setPeer1Username(user1.getUsername());
            chatPeerSession.setPeer2Uid(user2.getId());
            chatPeerSession.setPeer2Avatar(user2.getAvatar());
            chatPeerSession.setPeer2Nickname(user2.getNickname());
            chatPeerSession.setPeer2Username(user2.getUsername());
            chatPeerSession.setSessionTitle("");
            ChatPeerSession.DAO.insert(chatPeerSession);
        }

        return chatPeerSession;
    }

}
