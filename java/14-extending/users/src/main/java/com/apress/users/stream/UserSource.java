package com.apress.users.stream;

import com.apress.users.config.UserProperties;
import com.apress.users.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class UserSource {

    private UserProperties userProperties;

    public UserSource(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    @Bean
    public Supplier<User> processor(){
        return () -> {
            return new User();
        };
    }

}
