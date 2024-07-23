package com.apress.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
class UsersSmokeTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() throws Exception {
        assertThat(context).isNotNull();
    }

    @Configuration
    static class UserTestConfiguration {

        @Bean
        @ServiceConnection
        PostgreSQLContainer<?> postgreSQLContainer(){
            return new PostgreSQLContainer<>("postgres:latest");
        }
    }
}
