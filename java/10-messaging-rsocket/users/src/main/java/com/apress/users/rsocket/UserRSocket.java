package com.apress.users.rsocket;


import com.apress.users.model.User;
import com.apress.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Controller
public class UserRSocket {

    UserService userService;

    @MessageMapping("new-user")
    Mono<User> newUser(final User user){
        return this.userService.saveUpdateUser(user);
    }

    @MessageMapping("all-users")
    Flux<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @MessageMapping("user-by-email")
    Mono<User> findUserByEmail(final String email){
        return this.userService.findUserByEmail(email);
    }

    @MessageMapping("remove-user-by-email")
    Mono<Void> removeUserByEmail(final String email){
        this.userService.removeUserByEmail(email);
        return Mono.empty();
    }

}
