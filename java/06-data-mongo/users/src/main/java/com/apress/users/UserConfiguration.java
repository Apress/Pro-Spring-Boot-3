package com.apress.users;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

import java.util.Arrays;

@Configuration
public class UserConfiguration implements BeforeConvertCallback<User> {

    @Bean
    ApplicationListener<ApplicationReadyEvent> init(UserRepository userRepository){
        return applicationReadyEvent -> {
            userRepository.save(User.builder()
                    .email("ximena@email.com")
                    .name("Ximena")
                    .password("aw2s0meR!")
                    .role(UserRole.USER)
                    .active(true)
                    .build());
            userRepository.save(User.builder()
                    .email("norma@email.com")
                    .name("Norma")
                    .password("aw2s0meR!")
                    .role(UserRole.USER)
                    .role(UserRole.ADMIN)
                    .active(true)
                    .build());
        };
    }

    @Override
    public User onBeforeConvert(User entity, String collection) {
        if (entity.getGravatarUrl()==null)
            entity.setGravatarUrl(UserGravatar.getGravatarUrlFromEmail(entity.getEmail()));

        if (entity.getUserRole() == null)
            entity.setUserRole(Arrays.asList(UserRole.INFO));

        return entity;
    }
}

