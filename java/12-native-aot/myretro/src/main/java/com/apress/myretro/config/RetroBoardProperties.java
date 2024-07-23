package com.apress.myretro.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;


@Data
@ConfigurationProperties(prefix = "myretro")
public class RetroBoardProperties {
    @NestedConfigurationProperty
    private UsersService usersService;
}
