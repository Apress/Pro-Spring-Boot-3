package com.apress.myretro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="service")
@Data
public class MyRetroProperties {
    UsersConfiguration users;
}
