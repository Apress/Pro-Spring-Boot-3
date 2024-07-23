package com.apress.myretro.rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.service.RSocketServiceProxyFactory;

@Slf4j
@Configuration
public class Config {

    @Bean
    public RSocketServiceProxyFactory getRSocketServiceProxyFactory(RSocketRequester.Builder requestBuilder, @Value("${myretro.users-service.host:localhost}")String host, @Value("${myretro.users-service.port:9898}")int port) {
        RSocketRequester requester = requestBuilder.tcp(host, port);
        return RSocketServiceProxyFactory.builder(requester)
                .build();
    }

    @Bean
    public UserClient getClient(RSocketServiceProxyFactory factory) {
        return factory.createClient(UserClient.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserClient userClient) {
        return args -> {
            userClient.getAllUsers().doOnNext(
                    user -> log.info("User: {}", user)
            ).subscribe();
        };
    }
}
