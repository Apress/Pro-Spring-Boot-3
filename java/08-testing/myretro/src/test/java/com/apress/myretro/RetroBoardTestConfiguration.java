package com.apress.myretro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MongoDBContainer;

@Profile({"!mongoTest"})
@Configuration
public class RetroBoardTestConfiguration {

    @Bean
    @RestartScope
    @ServiceConnection
    public MongoDBContainer mongoDBContainer(){
        return new MongoDBContainer("mongo:latest");
    }


    public static void main(String[] args) {
        SpringApplication.from(MyretroApplication::main).run(args);
    }
}
