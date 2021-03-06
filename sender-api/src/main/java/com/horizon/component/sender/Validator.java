package com.horizon.component.sender;


/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/6/16
 */
public interface Validator<T extends MimeMessage> {

    boolean isSupported(String type) throws UnsupportedOperationException;

    /**
     * validate the handler
     *
     * @throws Exception
     */
    void validate(T mimeMessage) throws Exception;

    /**
     * validate the requirement parameters
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    void validateRequiredParameters(T mimeMessage) throws IllegalArgumentException;

    /**
     * validate the template parameters
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    void validateTemplateParameters(T mimeMessage) throws IllegalArgumentException;

    /**
     * validate the invalid parameters
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    void validateInvalidParameters(T mimeMessage) throws IllegalArgumentException;

}
