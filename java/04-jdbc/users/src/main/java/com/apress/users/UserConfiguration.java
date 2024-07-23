package com.apress.users;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    ApplicationListener<ApplicationReadyEvent> init(SimpleRepository userRepository) {
        return applicationReadyEvent -> {
            User ximena = User.builder()
                    .email("ximena@email.com")
                    .name("Ximena")
                    .password("aw2s0meR!")
                    .active(true)
                    .role(UserRole.USER)
                    .build();

            userRepository.save(ximena);

            User norma = User.builder()
                    .email("norma@email.com")
                    .name("Norma")
                    .password("aw2s0meR!")
                    .active(true)
                    .role(UserRole.USER)
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.save(norma);
        };
    }
}
