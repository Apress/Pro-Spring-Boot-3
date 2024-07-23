package com.apress.myretro.client;

import com.apress.myretro.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserClient {
    private RestTemplate restTemplate;
    private String server;

    public UserClient(@Value("${user.server:http://localhost:8080}") String server) {
       this.restTemplate = new RestTemplate();
       this.server = server;
    }

    public User getUserByEmail(String email){
        ResponseEntity<User> responseEntity = this.restTemplate.getForEntity(this.server + "/users/" + email,User.class);
        return responseEntity.getBody();
    }
}
