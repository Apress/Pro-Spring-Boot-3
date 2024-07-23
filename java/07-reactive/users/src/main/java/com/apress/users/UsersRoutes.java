package com.apress.users;

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

    private final UserRepository userRepository;

    @Bean
    public RouterFunction<ServerResponse> getUsersRoute() {
        return route(GET("/users"), request -> ServerResponse.ok()
                .body(this.userRepository.findAll(),User.class));
    }

    @Bean
    public RouterFunction<ServerResponse> postUserRoute() {
        return route(POST("/users"), request -> request
                .body(toMono(User.class))
                .flatMap(this.userRepository::save)
                .then(ServerResponse.ok().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> findUserByEmail(){
        return route(GET("/users/{email}"), request -> ServerResponse.ok()
                .body(this.userRepository.findByEmail(request.pathVariable("email")),User.class));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteUserByEmail(){
        return route(DELETE("/users/{email}"), request -> {
            this.userRepository.deleteByEmail(request.pathVariable("email"));
            return ServerResponse.noContent().build();
        });
    }

}

