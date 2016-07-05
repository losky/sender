package com.horizon.container.listener;

import com.horizon.container.event.EventObject;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/7/4
 */
public interface EventListener extends java.util.EventListener {

    void handleEvent(EventObject event);
}
