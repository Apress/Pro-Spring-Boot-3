package com.apress.users.config;

import com.apress.users.model.User;
import com.apress.users.model.UserGravatar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
public class UserConfiguration {

    @Bean
    BeforeConvertCallback<User> idGeneratingCallback() {
        return (user, sqlIdentifier) -> {
            if (user.getId() == null && (user.getGravatarUrl() == null || user.getGravatarUrl().isEmpty())) {
                return Mono.just(new User(UUID.randomUUID(),user.getEmail(), user.getName(), UserGravatar.getGravatarUrlFromEmail(user.getEmail()),user.getPassword(),user.getUserRole(),user.isActive()));
            }
            return Mono.just(user);
        };
    }

}
