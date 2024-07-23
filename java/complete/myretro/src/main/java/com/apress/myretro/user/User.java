package com.apress.myretro.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private String name;

    private String email;

    private String gravatarUrl;

    private String password;

    private List<UserRole> userRole = new ArrayList<>();

    private boolean active;
}
