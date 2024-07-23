package com.apress.users.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.apress.users.amqp.UserRabbitConfiguration.USERS_ACTIVATED;
import static com.apress.users.amqp.UserRabbitConfiguration.USERS_REMOVED;


@Slf4j
@AllArgsConstructor
@Component
public class UserLogs {

    private RabbitTemplate rabbitTemplate;

    @Async
    @EventListener
    void userActiveStatusEventHandler(UserActivatedEvent event){
        this.rabbitTemplate.convertAndSend(USERS_ACTIVATED,event);
        log.info("User {} active status: {}",event.getEmail(),event.isActive());
    }

    @Async
    @EventListener
    void userDeletedEventHandler(UserRemovedEvent event){
        this.rabbitTemplate.convertAndSend(USERS_REMOVED,event);
        log.info("User {} DELETED at {}",event.getEmail(),event.getRemoved());
    }

}
