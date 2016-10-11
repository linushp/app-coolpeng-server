package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.service.api.IChatMsgService;
import com.coolpeng.chat.utils.ChatConstant;
import com.coolpeng.chat.websocket.event.CreateSessionEvent;
import com.coolpeng.framework.event.TMSEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luanhaipeng on 16/10/11.
 */
@Service
public class RobotChatService implements IChatMsgService {

    @Override
    public TMSEvent saveMessage(UserEntity user, String msg, String msgSummary, String msgId, ChatSessionVO sessionVO) throws Exception {
        return null;
    }

    @Override
    public List<ChatMsgVO> getChatMsgList(ChatSessionVO sessionVO) throws Exception {
        List<ChatMsgVO> msgVOList = new ArrayList<>();
        msgVOList.add(new ChatMsgVO(ChatConstant.UBIBI_ROBOT_USER, "你好，我是哔哔机器人，如果无聊了就跟我聊聊吧", "001"));
        return msgVOList;
    }


    @Override
    public CreateSessionEvent createSession(UserEntity user, ChatSessionVO sessionVO) throws Exception {
        return null;
    }
}
