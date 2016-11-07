package com.coolpeng.chat.utils;

import com.coolpeng.chat.model.ChatUserVO;

/**
 * Created by luanhaipeng on 16/9/22.
 */
public class ChatConstant {
    public static final String DEFAULT_SESSION_ICON = "http://ubibi.coolpeng.cn/upload/user-1/3ef3887da1bbd49d9992751474080797076.jpg";
    public static final String UBIBI_ROBOT_ICON = "http://image.coolpeng.cn/avatar/mv-0001-1957/mv-0008.jpg";
    public static final String PUBLIC_CHANNEL_ICON1 = "http://image.coolpeng.cn/avatar/aa-0001-0504/aa-0372.jpg";
    public static final String PUBLIC_CHANNEL_ICON2 = "http://image.coolpeng.cn/avatar/fj-0001-0425/fj-0165.jpg";
    public static final ChatUserVO UBIBI_ROBOT_USER = new ChatUserVO("-1", "哔哔机器人", "哔哔机器人", UBIBI_ROBOT_ICON);
    public static final String UBIBI_ROBOT_HELLOWORLD = "你好，我是哔哔机器人，如果无聊了就跟我聊聊吧";

    public static final int CHAT_SESSION_MAX_MSG_COUNT = 100;
}
