package com.horizon.component.sender;


/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/6/7
 */
public interface Sender<T extends MimeMessage> {

    /**
     * send the send method
     *
     * @throws Exception
     */
    void send(T mimeMessage) throws Exception;

    /**
     * check the type is supported.
     *
     * @param sendType
     *
     * @return
     */
    boolean isSupported(String sendType);

}
