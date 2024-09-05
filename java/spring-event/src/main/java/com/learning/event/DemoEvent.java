package com.learning.event;

import org.springframework.context.ApplicationEvent;

public class DemoEvent extends ApplicationEvent {

    private String message;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DemoEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
