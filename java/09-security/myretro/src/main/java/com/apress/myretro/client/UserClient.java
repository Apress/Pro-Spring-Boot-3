package com.apress.myretro.client;

import com.apress.myretro.config.MyRetroProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    WebClient webClient;

    public UserClient(WebClient.Builder webClientBuilder, MyRetroProperties props) {
        this.webClient = webClientBuilder
                .baseUrl(props.getUsers().getServer())
                .defaultHeaders(headers -> headers.setBasicAuth(props.getUsers().getUsername(), props.getUsers().getPassword()))
                .build();
    }

    public Mono<User> getUserInfo(String email){
        return webClient.get()
                .uri("/users/{email}",email)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<String> getUserGravatar(String email){
        return webClient.get()
                .uri("/users/{email}/gravatar",email)
                .retrieve()
                .bodyToMono(String.class);
    }
}
