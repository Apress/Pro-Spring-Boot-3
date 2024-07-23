package com.apress.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    ApplicationListener<ApplicationReadyEvent> init(UserRepository userRepository) {
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

}
