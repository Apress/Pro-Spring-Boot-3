package com.apress.myretro.config;

import lombok.Data;

@Data
public class UsersProperties {
    String server;
    Integer port;
    String username;
    String password;
}
