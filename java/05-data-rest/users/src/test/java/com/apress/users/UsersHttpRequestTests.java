package com.apress.users;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersHttpRequestTests {

    private String baseUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() throws Exception {
       baseUrl = "/users";
    }


    @Test
    public void usersEndPointShouldReturnCollectionWithTwoUsers() throws Exception {
        ResponseEntity<CollectionModel<EntityModel<User>>> response =
                restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<CollectionModel<EntityModel<User>>>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaTypes.HAL_JSON);
        assertThat(response.getBody().getContent().size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void userEndPointPostNewUserShouldReturnUser() throws Exception {
        HttpHeaders createHeaders = new HttpHeaders();
        createHeaders.setContentType(MediaTypes.HAL_JSON);

        User user =  User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .gravatarUrl("https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build();


        HttpEntity<String> createRequest = new HttpEntity<>(convertToJson(user), createHeaders);
        ResponseEntity<EntityModel<User>> response =  this.restTemplate.exchange(baseUrl, HttpMethod.POST, createRequest, new ParameterizedTypeReference<EntityModel<User>>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        EntityModel<User> userResponse = response.getBody();
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getContent()).isNotNull();
        assertThat(userResponse.getLink("self")).isNotNull();

        assertThat(userResponse.getContent().getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    public void userEndPointDeleteUserShouldReturnVoid() throws Exception {

        this.restTemplate.delete(baseUrl + "/1");

        ResponseEntity<CollectionModel<EntityModel<User>>> response =
                restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<CollectionModel<EntityModel<User>>>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaTypes.HAL_JSON);
        assertThat(response.getBody().getContent().size()).isGreaterThanOrEqualTo(1);

    }

    @Test
    public void userEndPointFindUserShouldReturnUser() throws Exception{
        String email = "ximena@email.com";
        ResponseEntity<EntityModel<User>> response = restTemplate.exchange(baseUrl + "/search/findByEmail?email={email}", HttpMethod.GET, null, new ParameterizedTypeReference<EntityModel<User>>() {}, email);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        EntityModel<User> users = response.getBody();
        assertThat(users).isNotNull();
        assertThat(users.getContent().getEmail()).isEqualTo(email);
    }


    private String convertToJson(User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }
}
