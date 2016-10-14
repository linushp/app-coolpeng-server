package com.coolpeng.chat.listeners;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.entity.ChatPeerSession;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/10/14.
 */
public class UserInfoUpdateListener extends TMSEventListener {

    @Override
    public void onEvent(TMSEvent event) throws Exception {
        if ("UserInfoUpdateEvent".equals(event.getName())) {



            UserEntity userEntity = (UserEntity) event.getData();
            String uid = userEntity.getId();

            Map<String, Object> fieldsAndValue = new HashMap<>();
            fieldsAndValue.put("peer1Avatar",userEntity.getAvatar());
            fieldsAndValue.put("peer1Username",userEntity.getUsername());
            fieldsAndValue.put("peer1Nickname",userEntity.getNickname());
            ChatPeerSession.DAO.batchUpdateFields("peer1Uid", uid, fieldsAndValue);


            Map<String, Object> fieldsAndValue2 = new HashMap<>();
            fieldsAndValue2.put("peer2Avatar",userEntity.getAvatar());
            fieldsAndValue2.put("peer2Username",userEntity.getUsername());
            fieldsAndValue2.put("peer2Nickname",userEntity.getNickname());
            ChatPeerSession.DAO.batchUpdateFields("peer2Uid", uid, fieldsAndValue2);



        }
    }
}
