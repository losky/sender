package com.horizon.component.sender.basic;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /6/3
 */
public enum SendType {
    /**
     * Email send type.
     */
    EMAIL("com.horizon.component.sender.impl.email"),
    /**
     * Sms send type.
     */
    SMS("com.horizon.component.sender.impl.sms"),
    /**
     * Voice send type.
     */
    VOICE("com.horizon.component.sender.impl.voice");

    private String type;

    SendType(String type) {
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
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
