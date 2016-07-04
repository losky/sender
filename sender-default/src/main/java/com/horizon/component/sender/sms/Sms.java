package com.horizon.component.sender.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhenzhong on 2015/9/23.
 */
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

    public String getSmsServiceUrl() {
        return smsServiceUrl;
    }

    public void setSmsServiceUrl(String smsServiceUrl) {
        this.smsServiceUrl = smsServiceUrl;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getCli() {
        return cli;
    }

    public void setCli(String cli) {
        this.cli = cli;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
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
