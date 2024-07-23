package com.apress.myretro.client;

import com.apress.myretro.config.RetroBoardProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class UserClientConfig {

    @Bean
    WebClient webClient(RetroBoardProperties retroBoardProperties) {
        return WebClient.builder()
                .defaultHeaders(header ->
                        header.setBasicAuth(retroBoardProperties.getUsersService().getUsername(),
                                retroBoardProperties.getUsersService().getPassword()))
                .baseUrl(retroBoardProperties.getUsersService().getBaseUrl())
                .build();
    }

    @Bean
    UserClient userClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
                        .build();
        return httpServiceProxyFactory.createClient(UserClient.class);
    }
}
