package com.horizon.component.sender.basic;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Template;
import lombok.Data;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /3/2
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JacksonXmlRootElement(localName = "mine_message")
@Data
public class BasicMineMessage implements MimeMessage {

    private String accountId;
    private String content;
    private String from;
    private String to;
    private String purpose;
    private String sendType;
    private String subject;
    private Template template;


}
