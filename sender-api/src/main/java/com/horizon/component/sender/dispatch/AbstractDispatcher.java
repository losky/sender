package com.horizon.component.sender.dispatch;

import com.horizon.component.sender.Dispatcher;
import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ServiceLoader;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/7/3
 */
public abstract class AbstractDispatcher implements Dispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDispatcher.class);
    private static final ServiceLoader<Sender> sl = ServiceLoader.load(Sender.class);

    private static final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

    static {
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    }

    private MimeMessage mimeMessage;

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public AbstractDispatcher(MimeMessage mimeMessage) throws Exception {
        this.mimeMessage = mimeMessage;
        this.initial(mimeMessage);
    }

    /**
     * initial the handlers that implement from interface handler
     */
    protected abstract void initial(MimeMessage mimeMessage) throws Exception;

    /**
     * auto get the handler to send the sender
     *
     * @param sync
     *
     * @throws Exception
     */
    @Override
    public void dispatch(boolean sync) throws Exception {
        final Sender sender = getSender();
        if (sender == null) {
            throw new NullPointerException("Not found Sender \'" + mimeMessage.getSendType() + "\'.");
        }

        LOG.debug("Found '{}' send message: {}", sender, mimeMessage);
        if (sync)
            sender.send(mimeMessage);
        else {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        sender.send(mimeMessage);
                    } catch (Exception e) {
                        LOG.error("Send " + mimeMessage.getSendType() + " failed. The case: {}", e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * get instance of Sender via send type
     *
     * @return
     *
     * @throws Exception
     */
    private final Sender getSender() throws Exception {
        final String sendType = getType();
        for (Sender sender : sl) {
            if (sender.isSupported(sendType))
                return sender;
        }
        throw new NullPointerException("Not supported sender \'" + sendType + "\'.");
    }

    /**
     * get send type
     *
     * @return
     */
    private final String getType() {
        return mimeMessage.getSendType().toLowerCase();
    }
}