package com.apress.users;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersHttpRequestTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void indexPageShouldReturnHeaderOneContent() throws Exception {
        webTestClient.get().uri("/")
                        .exchange()
                                .expectStatus().isOk()
                        .expectBody(String.class).value( value -> {
                    assertThat(value).contains("Simple Users Rest Application");
                });
    }

    @Test
    public void usersEndPointShouldReturnCollectionWithTwoUsers() throws Exception {
        webTestClient.get().uri("/users")
                .exchange().expectStatus().isOk()
                .expectBody(Collection.class).value( collection -> {
                   assertThat(collection.size()).isGreaterThanOrEqualTo(3);
                });
    }

    @Test
    public void userEndPointPostNewUserShouldReturnUser() throws Exception {
        webTestClient.post().uri("/users")
                .body(Mono.just(new User(null,"dummy@email.com","Dummy",null,"aw2s0me", Arrays.asList(UserRole.USER),true)),User.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    public void userEndPointDeleteUserShouldReturnVoid() throws Exception {
        webTestClient.delete().uri("/users/norma@email.com")
                .exchange().expectStatus().isNoContent();
        
    }

    @Test
    public void userEndPointFindUserShouldReturnUser() throws Exception{
        webTestClient.get().uri("/users/ximena@email.com")
                .exchange().expectStatus().isOk()
                .expectBody(User.class).value( user -> {
                    assertThat(user).isNotNull();
                    assertThat(user.email()).isEqualTo("ximena@email.com");
                });
    }
}
