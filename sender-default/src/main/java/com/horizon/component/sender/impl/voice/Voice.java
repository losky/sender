package com.horizon.component.sender.impl.voice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhenzhong on 2015/9/24.
 */
public class Voice {

    public String url;
    public String service;
    public String receiver;
    public String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
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
