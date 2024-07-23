package com.apress.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class UsersRoutes {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UsersHandler usersHandler) {
        return route().nest(RequestPredicates.path("/users"), builder -> {
            builder.GET("",accept(APPLICATION_JSON), usersHandler::findAll);
            builder.GET("/{email}",accept(APPLICATION_JSON), usersHandler::findUserByEmail);
            builder.POST("", usersHandler::save);
            builder.DELETE("/{email}", usersHandler::deleteByEmail);
        }).build();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
