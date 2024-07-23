package com.apress.myretro.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.JmsListeners;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListeners {


    @JmsListeners({
            @JmsListener(destination = "${jms.user-events.queue.1}"),
            @JmsListener(destination = "${jms.user-events.queue.2}")
    })
    public void onUserEvent(UserEvent userEvent) {
       log.info("UserEventListeners.onUserEvent: {}", userEvent);
    }
}
