package com.horizon.component.sender.impl.voice;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhenzhong on 2015/9/24.
 */
@Data
public class Voice {

    /**
     * The Url.
     */
    public String url;
    /**
     * The Service.
     */
    public String service;
    /**
     * The Receiver.
     */
    public String receiver;
    /**
     * The Content.
     */
    public String content;


    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        try {
            buffer.append(url).append("?service=").append(service)
                    .append("&id=").append(receiver)
                    .append("&otp_code=").append(URLEncoder.encode(content, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return buffer.toString();
    }
}
