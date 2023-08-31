package com.horizon.component.sender.impl.voice;


import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Sender;
import com.horizon.component.sender.impl.email.EmailSender;
import com.horizon.component.utilities.HttpClientConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * The type Voice sender.
 */
public class VoiceSender implements Sender<MimeMessage> {

    private static final Logger LOG = LoggerFactory
            .getLogger(VoiceSender.class);

    private static final Voice voice;


    static {
        Properties properties = new Properties();
        voice = new Voice();
        try {
            LOG.info("Initialize the voice config.");
            properties.load(EmailSender.class.getClassLoader().getResourceAsStream("voice.properties"));
        } catch (IOException e) {
            LOG.error("Load config file error: {}", e.toString());
        }
        voice.setUrl(properties.getProperty("voice.http.url"));
        voice.setService(properties.getProperty("voice.http.service"));
    }


    /**
     * prepare for send()
     *
     * @return
     */
    private String prepare(String receiver, String content) {
        voice.setReceiver(receiver);
        voice.setContent(content);
        return voice.toString();
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
        LOG.info("Prepare ivr config:{}", mimeMessage.toString());
        String url = prepare(mimeMessage.getTo(), mimeMessage.getContent());
        LOG.info("Start to send ivr...{}", url);
        StringBuffer RtnStr;
        try {
            RtnStr = HttpClientConnection.getHttpResponse(url);
            LOG.debug("<VoiceSender> return: " + RtnStr.toString());
        } catch (UnknownHostException e) {
            LOG.error("<VoiceSender> UnknownHostException encountered, message={}", e.getMessage());
            throw e;
        } catch (IOException e) {
            LOG.error("<VoiceSender> IOException encountered, message={}", e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            LOG.error("<VoiceSender> Exception encountered, message={}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
        LOG.info("send voice successful.");

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
        return "voice".equalsIgnoreCase(sendType) ? true : false;
    }
}
