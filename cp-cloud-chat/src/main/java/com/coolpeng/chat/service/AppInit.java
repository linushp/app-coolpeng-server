package com.coolpeng.chat.service;


import com.coolpeng.appbase.StaticConfigManager;
import com.coolpeng.chat.utils.ChatConstant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Created by luanhaipeng on 16/10/10.
 */
@Service
public class AppInit implements InitializingBean {

    public void afterPropertiesSet() throws Exception {
        StaticConfigManager.pushInfo("UBIBI_ROBOT_USER", ChatConstant.UBIBI_ROBOT_USER);
    }
}