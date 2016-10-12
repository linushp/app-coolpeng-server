package com.coolpeng.chat.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatUserVO;
import com.coolpeng.chat.websocket.WebsocketContainer;
import com.coolpeng.framework.utils.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by luanhaipeng on 16/10/12.
 */
@Service
public class ChatUserService {


    public List<ChatUserVO> getChatUserList(UserEntity currentLoginUser) throws Exception {

        Collection<ChatUserVO> socketUserVOList = WebsocketContainer.getAllOnlineUserVO();

        List<Object> onLineUidList = CollectionUtil.getPropValueList(socketUserVOList, "uid");
        Set<Object> onLineUidSet = new HashSet<>(onLineUidList);


        List<UserEntity> userEntityList = UserEntity.DAO.findAll();
        Collections.sort(userEntityList, new Comparator<UserEntity>() {
            @Override
            public int compare(UserEntity o1, UserEntity o2) {
                int c1 = o1.getViewCount();
                int c2 = o2.getViewCount();
                return c2 - c1;
            }
        });

        List<ChatUserVO> offLineUserVOList = new ArrayList<>();
        List<ChatUserVO> onLineUserVOList = new ArrayList<>();

        for (UserEntity entity : userEntityList) {
            String uid = entity.getId();
            if (onLineUidSet.contains(uid)) {
                onLineUserVOList.add(new ChatUserVO(entity, ChatUserVO.STATUS_ON_LINE));
            } else {
                offLineUserVOList.add(new ChatUserVO(entity, ChatUserVO.STATUS_OFF_LINE));
            }
        }


        List<ChatUserVO> result = new ArrayList<>();
        result.addAll(onLineUserVOList);
        result.addAll(offLineUserVOList);


        if (result.size() > 100) {
            result = result.subList(0, 100);
        }

        return result;
    }
}
