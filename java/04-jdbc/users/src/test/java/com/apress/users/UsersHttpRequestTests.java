package com.apress.users;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersHttpRequestTests {

    @Value("${local.server.port}")
    private int port;

    private final String BASE_URL = "http://localhost:";
    private final String USERS_PATH = "/users";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void indexPageShouldReturnHeaderOneContent() throws Exception {
        assertThat(this.restTemplate.getForObject(BASE_URL + port,
                String.class)).contains("Simple Users Rest Application");
    }

    @Test
    public void usersEndPointShouldReturnCollectionWithTwoUsers() throws Exception {
        Collection<User> response = this.restTemplate.
                getForObject(BASE_URL + port + USERS_PATH, Collection.class);

        assertThat(response.size()).isGreaterThan(1);
    }

    @Test
    public void shouldRetrunErrorWhenPostBadUserForm() throws Exception {
        assertThatThrownBy(() -> {
            User user =  User.builder()
                    .email("bademail")
                    .name("Dummy")
                    .active(true)
                    .password("aw2s0")
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password must be at least 8 characters long and contain at least one number, one uppercase, one lowercase and one special character");
    }

    @Test
    public void userEndPointPostNewUserShouldReturnUser() throws Exception {

        User user =  User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .password("aw2s0meR!")
                .active(true)
                .role(UserRole.USER)
                .build();

        User response =  this.restTemplate.postForObject(BASE_URL + port + USERS_PATH,user,User.class);

        assertThat(response).isNotNull();
        assertThat(response.email()).isEqualTo(user.email());

        Collection<User> users = this.restTemplate.
                getForObject(BASE_URL + port + USERS_PATH, Collection.class);

        assertThat(users.size()).isGreaterThanOrEqualTo(2);

    }

    @Test
    public void userEndPointDeleteUserShouldReturnVoid() throws Exception {
        this.restTemplate.delete(BASE_URL + port + USERS_PATH + "/norma@email.com");

        Collection<User> users = this.restTemplate.
                getForObject(BASE_URL + port + USERS_PATH, Collection.class);

        assertThat(users.size()).isLessThanOrEqualTo(2);
    }

    @Test
    public void userEndPointFindUserShouldReturnUser() throws Exception{
        User user = this.restTemplate.getForObject(BASE_URL + port + USERS_PATH + "/1",User.class);

        assertThat(user).isNotNull();
        assertThat(user.email()).isEqualTo("ximena@email.com");
    }
}
