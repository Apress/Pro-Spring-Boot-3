package com.apress.users;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
    }

    @Test
    public void userEndPointPostNewUserShouldReturnUser() throws Exception {
        User user =  User.builder().email("dummy@email.com").name("Dummy").password("aw2s0m3R!").build();
        User response =  this.restTemplate.postForObject(BASE_URL + port + USERS_PATH,user,User.class);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(user.getEmail());

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
        User user = this.restTemplate.getForObject(BASE_URL + port + USERS_PATH + "/ximena@email.com",User.class);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("ximena@email.com");
    }


    @Test
    public void userEndPointPostNewUserShouldReturnBadUserResponse() throws Exception {
        User user =  User.builder().email("dummy@email.com").name("Dummy").password("aw2s0m").build();
        Map response =  this.restTemplate.postForObject(BASE_URL + port + USERS_PATH,user, Map.class);

        assertThat(response).isNotNull();
        assertThat(response.get("errors")).isNotNull();
        Map errors = (Map) response.get("errors");
        assertThat(errors.get("password")).isNotNull();
        assertThat(errors.get("password")).isEqualTo("Password must be at least 8 characters long and contain at least one number, one uppercase, one lowercase and one special character");
    }
}
