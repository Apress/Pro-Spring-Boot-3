package com.apress.myretro.client;

import com.apress.myretro.client.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service")
public interface UserClient {


    @GetMapping("/users")
    ResponseEntity<Iterable<User>> getAllUsers();

    @GetMapping("/users/{email}")
    ResponseEntity<User> getById(@PathVariable String email);
}
