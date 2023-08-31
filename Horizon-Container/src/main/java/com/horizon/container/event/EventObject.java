package com.horizon.container.event;


/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /7/4
 */
public class EventObject extends java.util.EventObject {

    /**
     * Constructs a prototypical EventObject.
     *
     * @param source The object on which the EventObject initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EventObject(Object source) {
        super(source);
    }

    /**
     * Log.
     */
    public void log() {

    }
}
