package com.coolpeng.app.base;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by luanhaipeng on 16/7/6.
 */
public class ReqParams {

    //都是必填字段
    private String uuid;  //phonegap自动获取，browser自动生成
    private String devicePlatform;  //phonegap自动获取，browser自动生成
    private String tokenId;


    public ReqParams() {
    }

    private ReqParams(String uuid, String devicePlatform, String tokenId) {
        this.uuid = uuid;
        this.devicePlatform = devicePlatform;
        this.tokenId = tokenId;
    }


    public static ReqParams parse(JSONObject jsonObject) {
        String tokenId = jsonObject.getString("TMS_APP_COMMON__TOKEN_ID");
        String devicePlatform = jsonObject.getString("TMS_APP_COMMON__DEVICE_PLATFORM");
        String deviceUuid = jsonObject.getString("TMS_APP_COMMON__DEVICE_UUID");
        return new ReqParams(deviceUuid, devicePlatform, tokenId);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
