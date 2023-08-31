package com.horizon.component.sender;


/**
 * interface defined by
 *
 * @param <T> the type parameter
 * @author ZhenZhong
 * @date 2016 /6/16
 */
public interface Validator<T extends MimeMessage> {

    /**
     * Is supported boolean.
     *
     * @param type the type
     * @return the boolean
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    boolean isSupported(String type) throws UnsupportedOperationException;

    /**
     * validate the handler
     *
     * @param mimeMessage the mime message
     * @throws Exception the exception
     */
    void validate(T mimeMessage) throws Exception;

    /**
     * validate the requirement parameters
     *
     * @param mimeMessage the mime message
     * @throws IllegalArgumentException the illegal argument exception
     */
    void validateRequiredParameters(T mimeMessage) throws IllegalArgumentException;

    /**
     * validate the template parameters
     *
     * @param mimeMessage the mime message
     * @throws IllegalArgumentException the illegal argument exception
     */
    void validateTemplateParameters(T mimeMessage) throws IllegalArgumentException;

    /**
     * validate the invalid parameters
     *
     * @param mimeMessage the mime message
     * @throws IllegalArgumentException the illegal argument exception
     */
    void validateInvalidParameters(T mimeMessage) throws IllegalArgumentException;

}
