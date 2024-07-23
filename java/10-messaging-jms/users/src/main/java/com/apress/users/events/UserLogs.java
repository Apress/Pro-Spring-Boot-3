package com.apress.users.events;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.apress.users.jms.UserJmsConfig.DESTINATION_ACTIVATED;
import static com.apress.users.jms.UserJmsConfig.DESTINATION_REMOVED;


@AllArgsConstructor
@Slf4j
@Component
public class UserLogs {

    private JmsTemplate jmsTemplate;

    @Async
    @EventListener
    void userActiveStatusEventHandler(UserActivatedEvent event){
        this.jmsTemplate.convertAndSend(DESTINATION_ACTIVATED,event);
        log.info("User {} active status: {}",event.getEmail(),event.isActive());
    }

    @Async
    @EventListener
    void userDeletedEventHandler(UserRemovedEvent event){
        this.jmsTemplate.convertAndSend(DESTINATION_REMOVED,event);
        log.info("User {} DELETED at {}",event.getEmail(),event.getRemoved());
    }

}
