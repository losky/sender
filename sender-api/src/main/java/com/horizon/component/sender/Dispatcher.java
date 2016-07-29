package com.horizon.component.sender;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/7/2
 */
public interface Dispatcher {

    /**
     * sender dispatcher
     *
     * @throws Exception
     */
    void dispatch(MimeMessage mimeMessage, boolean sync) throws Exception;
}
