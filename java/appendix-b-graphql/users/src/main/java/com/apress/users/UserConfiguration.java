package com.apress.users;


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
                    .gravatarUrl("https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar")
                    .password("aw2s0meR!")
                    .role(UserRole.USER)
                    .active(true)
                    .build());
            userRepository.save(User.builder()
                    .email("norma@email.com")
                    .name("Norma")
                    .gravatarUrl("https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar")
                    .password("aw2s0meR!")
                    .role(UserRole.USER)
                    .role(UserRole.ADMIN)
                    .active(true)
                    .build());
        };
    }

}
