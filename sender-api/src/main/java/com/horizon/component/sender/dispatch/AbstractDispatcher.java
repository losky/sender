package com.horizon.component.sender.dispatch;

import com.horizon.component.sender.Dispatcher;
import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Sender;
import com.horizon.component.utilities.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

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

    private TaskExecutor taskExecutor = SpringContextUtil.getBean("taskExecutor", TaskExecutor.class);
//
//    static {
//        taskExecutor.setCorePoolSize(5);
//        taskExecutor.setMaxPoolSize(10);
//        taskExecutor.setQueueCapacity(100);
//        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//        taskExecutor.initialize();
//    }


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
    public void dispatch(final MimeMessage mimeMessage, boolean sync) throws Exception {
        this.initial(mimeMessage);
        final Sender sender = getSender(mimeMessage.getSendType().toLowerCase());
        if (sender == null) {
            throw new NullPointerException("Not found Sender \'" + mimeMessage.getSendType() + "\'.");
        }
        if (sync) {
            try {
                sender.send(mimeMessage);
            } catch (Exception e) {
                LOG.error("Send " + mimeMessage.getSendType() + " failed. The case: {}", e.getMessage());
                throw e;
            }
        } else {
            if (taskExecutor == null) {
                throw new NullPointerException("No such bean '" + taskExecutor.getClass().getSimpleName() + "' found in application context.");
            }
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
    private final Sender getSender(String type) throws Exception {
        final String sendType = type;
        for (Sender sender : sl) {
            if (sender.isSupported(sendType)) {
                LOG.debug("Found sender \'{}\'", sender);
                return sender;
            }
        }
        throw new NullPointerException("Not supported sender \'" + sendType + "\'.");
    }
}
