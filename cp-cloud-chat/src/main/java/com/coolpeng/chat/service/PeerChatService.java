package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.ChatPeerSession;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.model.ChatUserVO;
import com.coolpeng.chat.service.api.IChatMsgService;
import com.coolpeng.chat.websocket.event.CreateSessionEvent;
import com.coolpeng.chat.websocket.event.PeerMsgEvent;
import com.coolpeng.framework.event.TMSEvent;
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


    /**
     * @param sendMessageUser 发送消息的用户
     * @param msg
     * @param msgSummary
     * @param msgId
     * @param sessionVO
     * @return
     * @throws Exception
     */
    @Override
    public TMSEvent saveMessage(UserEntity sendMessageUser, String msg, String msgSummary, String msgId, ChatSessionVO sessionVO,boolean isRefreshRecent) throws Exception {
        String entityId = sessionVO.getEntityId();
        ChatPeerSession chatSessionInfo = ChatPeerSession.DAO.queryObjectByKV("id", entityId);
        ChatMsgVO chatMsgVO = new ChatMsgVO(sendMessageUser, msg, msgId);
        String currentUid = sendMessageUser.getId();
        String receiveUserId = null;
        ChatUserVO receiveUserVO = null;
        ChatUserVO sendUserVO = new ChatUserVO(sendMessageUser);
        if (currentUid.equals(chatSessionInfo.getPeer1Uid())) {
            receiveUserId = chatSessionInfo.getPeer2Uid();
            receiveUserVO = chatSessionInfo.pickOutPeer2Uid();
        } else {
            receiveUserId = chatSessionInfo.getPeer1Uid();
            receiveUserVO = chatSessionInfo.pickOutPeer1Uid();
        }

        chatSessionMessageService.saveSessionMessage(sendMessageUser, sessionVO, chatMsgVO);


        ChatSessionVO sessionVO1 = toChatSessionVO(sessionVO, chatMsgVO, receiveUserVO); //sender
        ChatSessionVO sessionVO2 = toChatSessionVO(sessionVO, chatMsgVO, sendUserVO);  //receive
        sessionVO1.setLastMsgText(msgSummary);
        sessionVO2.setLastMsgText(msgSummary);
        if (isRefreshRecent) {
            recentSessionService.asynSaveRecentSession(currentUid,sessionVO1);
            recentSessionService.asynSaveRecentSession(receiveUserId,sessionVO2);
        }

        //只给一个人发送这个事件
        return new PeerMsgEvent(chatMsgVO, receiveUserId, msgSummary,sessionVO2);
    }

    private ChatSessionVO toChatSessionVO(ChatSessionVO sessionVO, ChatMsgVO chatMsgVO, ChatUserVO anotherUser) {
        ChatSessionVO vo = new ChatSessionVO(sessionVO,chatMsgVO);
        vo.setSessionTitle("与 " + anotherUser.getNickname() + " 对话");
        vo.setSessionIcon(anotherUser.getAvatar());
        return vo;
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

        ChatSessionVO sessionVO1 = toChatSessionVO(chatPeerSession, participateUidList, user2);
        ChatSessionVO sessionVO2 = toChatSessionVO(chatPeerSession, participateUidList, user1);

        recentSessionService.doSaveRecentSession(uid1,sessionVO1);
        recentSessionService.doSaveRecentSession(uid2,sessionVO2);

        return new CreateSessionEvent(sessionVO1);
    }


    @Override
    public void deleteSession(UserEntity currentLoginUser,String sessionId) throws Exception {
        recentSessionService.deleteRecentSession(currentLoginUser.getId(), sessionId);
    }


    private ChatSessionVO toChatSessionVO(ChatPeerSession chatPeerSession, List<String> participateUidList, UserEntity anotherUser) {
        ChatSessionVO vo = new ChatSessionVO(ChatSessionVO.TYPE_PEER, chatPeerSession.getId(), "", "");
        vo.setSessionTitle("与 " + anotherUser.getNickname() + " 对话");
        vo.setSessionIcon(anotherUser.getAvatar());
        vo.setParticipateUidList(participateUidList);
        return vo;
    }


    private ChatPeerSession getOrCreateChatPeerSession(UserEntity user1, UserEntity user2) throws Exception {

        String uid1 = user1.getId();
        String uid2 = user2.getId();

        //select * from  ChatPeerSession where (peer1Uid = ui1 and peer2Uid = uid2) or (peer1Uid = ui2 and peer2Uid = uid1)
        ChatPeerSession chatPeerSession = ChatPeerSession.DAO.queryObjectByKV("peer1Uid", uid1, "peer2Uid", uid2);
        if (chatPeerSession == null) {
            chatPeerSession = ChatPeerSession.DAO.queryObjectByKV("peer2Uid", uid1, "peer1Uid", uid2);
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
