package com.apress.users;

import com.apress.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class UsersSmokeTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userService).isNotNull();
    }

}
