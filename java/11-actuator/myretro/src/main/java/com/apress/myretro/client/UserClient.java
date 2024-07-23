package com.apress.myretro.client;

import com.apress.myretro.client.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@HttpExchange(url = "/users", accept = "application/json", contentType = "application/json")
public interface UserClient {

    @GetExchange
    Flux<User> getAllUsers();

    @GetExchange("/{email}")
    Mono<User> getById(@PathVariable String email);
}
