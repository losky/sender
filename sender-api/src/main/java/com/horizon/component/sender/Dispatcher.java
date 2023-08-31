package com.horizon.component.sender;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /7/2
 */
public interface Dispatcher {

    /**
     * sender dispatcher
     *
     * @param mimeMessage the mime message
     * @param sync        the sync
     * @throws Exception the exception
     */
    void dispatch(MimeMessage mimeMessage, boolean sync) throws Exception;
}
