package com.horizon.component.sender.dispatch;


import com.horizon.component.sender.Dispatcher;
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

    // private static SenderDispatcher dispatcher = new SenderDispatcher();

    private PrepareSender prepareSender = new PrepareSender();

    private static class DispatcherHolder {
        private static final SenderDispatcher INSTANCE = new SenderDispatcher();
    }

    private SenderDispatcher() {
    }

    public static final Dispatcher getInstance() {
        return DispatcherHolder.INSTANCE;
    }


    /**
     * initial the handlers that implement from interface handler
     */
    protected void initial(MimeMessage mimeMessage) throws Exception {
        prepareSender.validate(mimeMessage);
    }

    /**
     * parse message from file or db
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    @Override
    protected void parseMessage(MimeMessage mimeMessage) throws Exception {
        prepareSender.ParseTemplateContent(mimeMessage);
    }

    public void dispatch(MimeMessage mimeMessage) throws Exception {
        super.dispatch(mimeMessage, true);
    }

}
