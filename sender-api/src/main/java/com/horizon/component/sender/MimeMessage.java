package com.horizon.component.sender;


import java.io.Serializable;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/2/29
 */
public interface MimeMessage extends Serializable {

    String getAccountId();

    String getContent();

    void setContent(String content);

    String getPurpose();

    String getTo();

    String getFrom();

    String getSendType();

    String getSubject();

    Template getTemplate();

}
