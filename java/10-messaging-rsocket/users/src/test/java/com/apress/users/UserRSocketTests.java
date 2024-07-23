package com.apress.users;


import com.apress.users.model.User;
import com.apress.users.model.UserRole;
import com.apress.users.repository.UserRepository;
import com.apress.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRSocketTests {


    @Autowired
    private RSocketRequester rSocketRequester;


    @Test
    public void testGetAllUsers() {
        // Send a request message
        Flux<User> result = rSocketRequester
                .route("all-users")
                .retrieveFlux(User.class);

        // Verify that the response message contains the expected data
        StepVerifier
                .create(result)
                .consumeNextWith(user -> {
                    assertThat(user.getId()).isNotNull();
                })
                .thenCancel()
                .verify();
    }


    @TestConfiguration
    static class ClientConfiguration {

        @Bean
        @Lazy
        public RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {

            RSocketRequester.Builder builder = RSocketRequester.builder();

            return builder
                    .rsocketStrategies(c -> {
                        c.encoder(new Jackson2JsonEncoder());
                        c.decoder(new Jackson2JsonDecoder());
                    })
                    .rsocketConnector(
                            rSocketConnector ->
                                    rSocketConnector.reconnect(Retry.indefinitely().doAfterRetry(e -> System.out.println(e.failure()))))
                    .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                    .tcp("localhost", 9898);

        }

        @Bean
        CommandLineRunner init(UserRepository userRepository){
            return args -> {
                userRepository.save(new User(null,"ximena@email.com","Ximena",null,"aw2s0me", Arrays.asList(UserRole.USER),true)).block();
                userRepository.save(new User(null,"norma@email.com","Norma" ,null, "aw2s0me", Arrays.asList(UserRole.USER, UserRole.ADMIN),false)).block();
                userRepository.save(new User(null,"dummy@email.com","Dummy" ,null, "aw2s0me", Arrays.asList(UserRole.USER, UserRole.ADMIN),false)).block();
            };
        }

    }
}
