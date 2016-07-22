package com.horizon.component.sender.impl.email;


import com.horizon.component.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 *
 */
public class EmailSender implements Sender<com.horizon.component.sender.MimeMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(EmailSender.class);

    private static final Session SESSION;

    private static final String from;

    static {
        Properties properties = new Properties();
        try {
            LOG.info("Initialize the email config.");
            properties.load(EmailSender.class.getClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            LOG.error("Load config file error: {}", e.toString());
        }
        from = properties.getProperty("mail.from");
        SESSION = Session.getDefaultInstance(properties,
                new EmailAuthenticator(properties.getProperty("mail.username"), properties.getProperty("mail.password")));
        LOG.info("Connect to the mail server ==> {}", properties.get("mail.smtp.host") + ":" + properties.get("mail.smtp.port"));
    }

    private void prepare(String from, String to, String subject, String content) throws MessagingException {
        MimeMessage mailMessage = new MimeMessage(SESSION);
        mailMessage.setFrom(new InternetAddress(from == null ? (this.from == null ? "do-not-replay@system.com" : this.from) : from));
        // Message.RecipientType.TO属性表示接收者的类型为TO
        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mailMessage.setSubject(subject == null ? "System notification" : subject, "UTF-8");
        mailMessage.setSentDate(new Date());
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        html.setContent(content.trim(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        mailMessage.setContent(mainPart);
        Transport.send(mailMessage);
    }

    /**
     * send the send method
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    @Override
    public void send(com.horizon.component.sender.MimeMessage mimeMessage) throws Exception {
        LOG.info("Prepare sender email:{}", mimeMessage.toString());
        LOG.info("Start to send email...");
        try {
            prepare(mimeMessage.getFrom(), mimeMessage.getTo(), mimeMessage.getSubject(), mimeMessage.getContent());
        } catch (Exception e) {
            LOG.error("send email failed.{}", e.getCause());
            throw e;
        }
        LOG.info("Send email successfully.");
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
        return "email".equalsIgnoreCase(sendType) ? true : false;
    }
}
