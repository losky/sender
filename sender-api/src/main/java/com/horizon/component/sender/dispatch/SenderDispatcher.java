package com.horizon.component.sender.dispatch;


import com.horizon.component.sender.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/6/7
 */
public class SenderDispatcher extends AbstractDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(SenderDispatcher.class);

    public SenderDispatcher(MimeMessage mimeMessage) throws Exception {
        super(mimeMessage);
    }

    /**
     * initial the handlers that implement from interface handler
     */
    protected void initial(MimeMessage mimeMessage) throws Exception {
        new PrepareSender(mimeMessage).validate().ParseTemplateContent();
    }

    public void dispatch() throws Exception {
        super.dispatch(true);
    }

}
