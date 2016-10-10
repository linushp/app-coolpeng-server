package com.coolpeng.appbase.controller.vo;

import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.UniqueId;

import java.util.Map;
import java.util.UUID;

/**
 * Created by luanhaipeng on 16/9/20.
 */
public class WebClientInfoVO {

    private String serverTimeString = DateUtil.currentTimeFormat();
    private long serverTimeMillis = System.currentTimeMillis();
    private String requestUniqueId = UniqueId.getOne();
    private String requestRandomUUID = "";
    private String clientIpAddress = "";

    private Map<String,Object> config;

    public WebClientInfoVO() {
        UUID uuid = UUID.randomUUID();
        this.requestRandomUUID = uuid.toString();
    }


    public String getServerTimeString() {
        return serverTimeString;
    }

    public void setServerTimeString(String serverTimeString) {
        this.serverTimeString = serverTimeString;
    }

    public long getServerTimeMillis() {
        return serverTimeMillis;
    }

    public void setServerTimeMillis(long serverTimeMillis) {
        this.serverTimeMillis = serverTimeMillis;
    }

    public String getRequestUniqueId() {
        return requestUniqueId;
    }

    public void setRequestUniqueId(String requestUniqueId) {
        this.requestUniqueId = requestUniqueId;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public String getRequestRandomUUID() {
        return requestRandomUUID;
    }

    public void setRequestRandomUUID(String requestRandomUUID) {
        this.requestRandomUUID = requestRandomUUID;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}
