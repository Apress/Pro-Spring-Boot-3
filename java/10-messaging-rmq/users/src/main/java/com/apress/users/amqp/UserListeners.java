package com.apress.users.amqp;

import com.apress.users.events.UserActivatedEvent;
import com.apress.users.events.UserRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.apress.users.amqp.UserRabbitConfiguration.USERS_ACTIVATED;
import static com.apress.users.amqp.UserRabbitConfiguration.USERS_EXCHANGE;
import static com.apress.users.amqp.UserRabbitConfiguration.USERS_REMOVED;
import static com.apress.users.amqp.UserRabbitConfiguration.USERS_REMOVED_QUEUE;
import static com.apress.users.amqp.UserRabbitConfiguration.USERS_STATUS_QUEUE;

@Component
@Slf4j
public class UserListeners {

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(name = USERS_STATUS_QUEUE, durable = "true", autoDelete = "false")
                    ,exchange = @Exchange(name=USERS_EXCHANGE,type = "topic"),key=USERS_ACTIVATED))
    public void userStatusEventProcessing(UserActivatedEvent activatedEvent){
        log.info("[AMQP - Event] Activated Event Received: {}", activatedEvent);
    }

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(name = USERS_REMOVED_QUEUE, durable = "true", autoDelete = "false")
                    ,exchange = @Exchange(name=USERS_EXCHANGE,type="topic"),key=USERS_REMOVED))
    public void userRemovedEventProcessing(UserRemovedEvent removedEvent){
        log.info("[AMQP - Event] Activated Event Received: {}", removedEvent);
    }

}
