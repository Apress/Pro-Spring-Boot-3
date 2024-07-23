package com.apress.myretro.rsocket;


import org.springframework.messaging.rsocket.service.RSocketExchange;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public interface UserClient {

    @RSocketExchange("all-users")
    Flux<User> getAllUsers();

}
