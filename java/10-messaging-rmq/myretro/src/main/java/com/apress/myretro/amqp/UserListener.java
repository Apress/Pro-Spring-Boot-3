package com.apress.myretro.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserListener {

    private static final String USERS_ALL = "users.*";
    private static final String USERS_ALL_QUEUE = "USER_ALL";
    private static final String USERS_EXCHANGE = "USERS";

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(name = USERS_ALL_QUEUE, durable = "true", autoDelete = "false")
                    ,exchange = @Exchange(name=USERS_EXCHANGE,type = "topic"),key=USERS_ALL))
    public void userStatusEventProcessing(UserEvent userEvent){
        log.info("[AMQP - Event] Received: {}", userEvent);
    }
}
