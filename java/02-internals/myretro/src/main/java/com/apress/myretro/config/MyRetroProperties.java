package com.apress.myretro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="service")
public class MyRetroProperties {
    private Users users;
}


@Data
class Users {
    private String server;

    private Integer port;

    private String username;

    private String password;


}