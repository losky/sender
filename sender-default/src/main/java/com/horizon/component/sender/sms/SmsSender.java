package com.horizon.component.sender.sms;


import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Sender;
import com.horizon.component.sender.email.EmailSender;
import com.horizon.component.utilities.HttpClientConnection;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class SmsSender implements Sender<MimeMessage> {

    private static final Logger LOG = LoggerFactory
            .getLogger(SmsSender.class);
    private static final Sms sms;


    static {
        Properties properties = new Properties();
        sms = new Sms();
        try {
            LOG.info("Initialize the config info.");
            properties.load(EmailSender.class.getResourceAsStream("sms.properties"));
        } catch (IOException e) {
            LOG.error("Load config file error: {}", e.toString());
        }
        sms.setSmsServiceUrl(properties.getProperty("sms.http.url"));
        sms.setApiKey(properties.getProperty("sms.http.url"));
        sms.setSecureKey(properties.getProperty("sms.http.secureKey"));
        sms.setApiVersion(properties.getProperty("sms.http.apiVersion"));
        sms.setSenderId(properties.getProperty("sms.http.sender"));
        sms.setCli(properties.getProperty("sms.http.cli"));
        sms.setCommand("sendsms");
        sms.setClientIp("globalroam.com");
    }


    private String prepare(String recipient, String content) {
        sms.setContent(content);
        sms.setReceiver(recipient);
        return sms.toString();
    }

    /**
     * send the send method
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    @Override
    public void send(MimeMessage mimeMessage) throws Exception {
        LOG.info("Prepare sender sms config:{}", mimeMessage.toString());

        String temp[];
        String url = prepare(mimeMessage.getTo(), mimeMessage.getContent());
        LOG.info("Start to send sms...{}", url);
        StringBuffer RtnStr;
        try {
            RtnStr = HttpClientConnection.getHttpResponse(url);
        } catch (Exception e) {
            LOG.error("SMS send failed: Case:{}", e.getCause());
            throw e;
        }
        LOG.debug("<SmsSender> return: " + RtnStr.toString());
        temp = (RtnStr.toString()).split("\n");
        if (!(temp[1] != null && (temp[1].trim()).equals("retcode=1000"))) {
            LOG.error("send sms failed.");
            throw new Exception("send sms failed." + ArrayUtils.toString(temp));
        }
        LOG.info("send sms successful.");
    }

    /**
     * check the type is supported.
     *
     * @param sendType
     *
     * @return
     */
    @Override
    public boolean isSupported(String sendType) {
        return "sms".equalsIgnoreCase(sendType) ? true : false;
    }
}
