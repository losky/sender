package com.horizon.component.sender.impl.sms;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The type Sms.
 *
 * @author zhenzhong
 * @date 2015 /9/23
 */
@Data
public class Sms {

    private String smsServiceUrl;

    private String senderId;

    private String cli;

    private String apiKey;

    private String secureKey;

    private String apiVersion;

    private String command;

    private String receiver;

    private String content;

    private String clientIp;


    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        try {
            buffer.append(smsServiceUrl).append("?func=proxy")
                    .append("&functype=proxy").append("&proxycmd=").append(command)
                    .append("&package=GRSMS").append("&domain=pfingo").append("&transId=").append(System.currentTimeMillis())
                    .append("&apiKey=").append(apiKey).append("&version=").append(apiVersion).append("&callerId=").append(cli)
                    .append("&destinationNumber=").append(URLEncoder.encode(receiver, "UTF-8")).append("&endClientIp=").append(clientIp)
                    .append("&message=").append(URLEncoder.encode(content, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return buffer.toString();
    }
}
