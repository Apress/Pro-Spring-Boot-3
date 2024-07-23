package com.apress.myretro.config;

import lombok.Data;

@Data
public class UsersService {
    private String baseUrl;
    private String basePath;
    private String username;
    private String password;
}