package com.apress.users.web;

import com.apress.users.model.User;
import com.apress.users.repository.UserRepository;
import com.apress.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class UsersRoutes {

    private final UserService userService;

    @Bean
    public RouterFunction<ServerResponse> getUsersRoute() {
        return route(GET("/users"), request -> ServerResponse.ok()
                .body(this.userService.getAllUsers(),User.class));
    }

    @Bean
    public RouterFunction<ServerResponse> postUserRoute() {
        return route(POST("/users"), request -> request
                .body(toMono(User.class))
                .flatMap(this.userService::saveUpdateUser)
                .then(ServerResponse.ok().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> findUserByEmail(){
        return route(GET("/users/{email}"), request -> ServerResponse.ok()
                .body(this.userService.findUserByEmail(request.pathVariable("email")), User.class));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteUserByEmail(){
        return route(DELETE("/users/{email}"), request -> {
            this.userService.removeUserByEmail(request.pathVariable("email"));
            return ServerResponse.noContent().build();
        });
    }

}