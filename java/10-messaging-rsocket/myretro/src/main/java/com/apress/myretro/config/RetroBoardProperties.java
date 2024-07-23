package com.apress.myretro.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "myretro")
public class RetroBoardProperties {
    private UsersService usersService;
}


@Data
class UsersService {
    private String hostname;
    private String basePath;
    private int port;
}