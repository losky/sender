package com.horizon.component.sender.dispatch;

import com.horizon.component.sender.Dispatcher;
import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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
        taskExecutor.initialize();
    }

    private MimeMessage mimeMessage;

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public AbstractDispatcher(MimeMessage mimeMessage) throws Exception {
        this.mimeMessage = mimeMessage;
//        this.initial(mimeMessage);
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
        this.initial(mimeMessage);
        final Sender sender = getSender();
        if (sender == null) {
            throw new NullPointerException("Not found Sender \'" + mimeMessage.getSendType() + "\'.");
        }
        if (sync) {
            sender.send(mimeMessage);
        } else {
            Future<String> result = taskExecutor.submit(new Callable<String>() {
                /**
                 * Computes a result, or throws an exception if unable to do so.
                 *
                 * @return computed result
                 *
                 * @throws Exception if unable to compute a result
                 */
                @Override
                public String call() throws Exception {
                    try {
                        sender.send(mimeMessage);
                    } catch (Exception e) {
                        LOG.error("Send " + mimeMessage.getSendType() + " failed. The case: {}", e.getMessage());
                        return e.getMessage();
                    }
                    return "success";
                }
            });
            LOG.info("result: {}", result.get());
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
            if (sender.isSupported(sendType)) {
                LOG.debug("Found sender \'{}\'", sender);
                return sender;
            }
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
