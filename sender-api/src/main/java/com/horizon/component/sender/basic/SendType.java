package com.horizon.component.sender.basic;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/6/3
 */
public enum SendType {
    EMAIL("com.horizon.component.sender.impl.email"),
    SMS("com.horizon.component.sender.impl.sms"),
    VOICE("com.horizon.component.sender.impl.voice");

    private String type;

    SendType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SendType{" +
                "type='" + type + '\'' +
                '}';
    }
}
