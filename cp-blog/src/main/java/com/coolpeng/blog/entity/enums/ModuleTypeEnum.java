package com.coolpeng.blog.entity.enums;

public enum ModuleTypeEnum {
    FORUM(1),
    ASK(2),
    BLOG(3),
    GOSSIP(4),
    WEIBO(5);

    private int value;

    private ModuleTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}