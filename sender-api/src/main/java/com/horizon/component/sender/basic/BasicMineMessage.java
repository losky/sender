package com.horizon.component.sender.basic;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Template;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/3/2
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JacksonXmlRootElement(localName = "mine_message")
public class BasicMineMessage implements MimeMessage {

    private String accountId;
    private String content;
    private String from;
    private String to;
    private String purpose;
    private String sendType;
    private String subject;
    private Template template;

    @Override
    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String getSubject() {
        return subject;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    @Override
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "BasicMineMessage{\n" +
                "accountId='" + accountId + "'\n" +
                ", content='" + content + "'\n" +
                ", from='" + from + "'\n" +
                ", to='" + to + "'\n" +
                ", purpose='" + purpose + "'\n" +
                ", sendType='" + sendType + "'\n" +
                ", subject='" + subject + "'\n" +
                ", template=" + template + "\n" +
                '}';
    }
}
