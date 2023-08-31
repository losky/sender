package com.horizon.component.sender;


/**
 * interface defined by
 *
 * @param <T> the type parameter
 * @author ZhenZhong
 * @date 2016 /6/7
 */
public interface Sender<T extends MimeMessage> {

    /**
     * send the send method
     *
     * @param mimeMessage the mime message
     * @throws Exception the exception
     */
    void send(T mimeMessage) throws Exception;

    /**
     * check the type is supported.
     *
     * @param sendType the send type
     * @return boolean boolean
     */
    boolean isSupported(String sendType);

}
