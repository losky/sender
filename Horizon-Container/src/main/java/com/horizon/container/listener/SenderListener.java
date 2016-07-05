package com.horizon.container.listener;

import com.horizon.container.event.EventObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/7/4
 */
public class SenderListener implements EventListener {
    private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

    @Override
    public void handleEvent(EventObject event) {
        LOG.info("handler event.");
        event.log();
    }
}
