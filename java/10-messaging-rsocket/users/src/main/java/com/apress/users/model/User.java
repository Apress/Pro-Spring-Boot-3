package com.apress.users.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="PEOPLE")
public class User {

    @Id
    UUID id;

    @NotBlank(message = "Email can not be empty")
    private String email;

    @NotBlank(message = "Name can not be empty")
    private String name;

    private String gravatarUrl;

    @NotBlank(message = "Password can not be empty")
    private String password;

    private Collection<UserRole> userRole = Collections.emptyList();

    private boolean active;
}
