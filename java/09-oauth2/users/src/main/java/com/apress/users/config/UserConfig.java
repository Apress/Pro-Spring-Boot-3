package com.apress.users.config;

import com.apress.users.model.User;
import com.apress.users.repository.UserRepository;
import com.apress.users.model.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner init(UserRepository userRepository){
        return args -> {
            userRepository.save(new User("ximena@email.com","Ximena","https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar","aw2s0meR!", Arrays.asList(UserRole.USER),true));
            userRepository.save(new User("norma@email.com","Norma" ,"https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar", "aw2s0meR!", Arrays.asList(UserRole.ADMIN),true));
        };
    }

}
