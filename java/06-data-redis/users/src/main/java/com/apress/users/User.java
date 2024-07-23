package com.apress.users;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Collection;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("USERS")
public class User {


    @Id
    @NotBlank(message = "Email can not be empty")
    private String email;

    @NotBlank(message = "Name can not be empty")
    private String name;

    private String gravatarUrl;

    @NotBlank(message = "Password can not be empty")
    private String password;

    @Singular("role")
    private Collection<UserRole> userRole;

    private boolean active;

}
