package com.apress.users.stream;

import com.apress.users.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


@Configuration
public class UserProcessor {

    @Bean
    public Function<String, User> process(){
        return value -> {
            return new User();
        };
    }
}
