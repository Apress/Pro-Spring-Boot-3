package com.apress.users;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionMetadata;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.r2dbc.connection.SingleConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

@Configuration
public class UserConfiguration {

    @Component
    class GravatarUrlGeneratingCallback implements BeforeConvertCallback<User>{

        @Override
        public Mono<User> onBeforeConvert(User user, SqlIdentifier sqlIdentifier) {

            if (user.id() == null && (user.gravatarUrl() == null || user.gravatarUrl().isEmpty())) {
                return Mono.just(user.withGravatarUrl(user.email()));
            }
            return Mono.just(user);
        }
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository){
        return args -> {

            userRepository.saveAll(Arrays.asList(new User(null,"ximena@email.com","Ximena",null,"aw2s0me", Arrays.asList(UserRole.USER),true)
            ,new User(null,"norma@email.com","Norma" ,null, "aw2s0me", Arrays.asList(UserRole.USER, UserRole.ADMIN),true)))
                    .blockLast(Duration.ofSeconds(10));
        };
    }

}
