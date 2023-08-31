package com.horizon.component.sender;


import java.io.Serializable;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /2/29
 */
public interface MimeMessage extends Serializable {

    /**
     * Gets account id.
     *
     * @return the account id
     */
    String getAccountId();

    /**
     * Gets content.
     *
     * @return the content
     */
    String getContent();

    /**
     * Sets content.
     *
     * @param content the content
     */
    void setContent(String content);

    /**
     * Gets purpose.
     *
     * @return the purpose
     */
    String getPurpose();

    /**
     * Gets to.
     *
     * @return the to
     */
    String getTo();

    /**
     * Gets from.
     *
     * @return the from
     */
    String getFrom();

    /**
     * Gets send type.
     *
     * @return the send type
     */
    String getSendType();

    /**
     * Gets subject.
     *
     * @return the subject
     */
    String getSubject();

    /**
     * Gets template.
     *
     * @return the template
     */
    Template getTemplate();

}
