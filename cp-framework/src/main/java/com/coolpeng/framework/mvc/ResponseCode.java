package com.coolpeng.framework.mvc;


public enum ResponseCode {


    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败
     */
    ERROR(10001, "失败"),

    /**
     * "登录失败，公司名不存在或用户名密码错误！"
     */
    ERROR_LOGIN_ERROR_PASSWORD(10002, "登录失败，公司名不存在或用户名密码错误！"),


    /**
     * 此公司实例没有开通
     */
    ERROR_INSTANCE_STATUS(10003, "此公司实例没有开通"),


    /**
     * 此公司的NODE节点不存在
     */
    ERROR_INSTANCE_NODE(10004, "此公司的NODE节点不存在"),


    /**
     * 检测到新版本，建议更新新版本
     */
    ERROR_VERSION_NEW(20005, "检测到新版本，建议更新新版本"),

    /**
     * 此版本已经过时，更新新版本后才能使用
     */
    ERROR_VERSION_UPDATE(20006, "此版本已经过时，更新新版本后才能使用");


    private int value;
    private String text;


    private ResponseCode(int value, String text) {
        this.value = value;
        this.text = text;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    /**
     * 根据int型的code获取所对应的常量
     *
     * @param value
     * @return
     */
    public static ResponseCode getResponseConstByValue(int value) {
        ResponseCode[] allConst = ResponseCode.values();
        for (ResponseCode c : allConst) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return null;
    }

    /**
     * 根据int型的code获取所对应的常量
     *
     * @param text
     * @return
     */
    public static ResponseCode getResponseConstByText(String text) {
        ResponseCode[] allConst = ResponseCode.values();
        for (ResponseCode c : allConst) {
            if (c.getText().equals(text)) {
                return c;
            }
        }
        return null;
    }

}
