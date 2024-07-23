package com.apress.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="PEOPLE")
public class User {


    @Id
    @NotBlank(message = "Email can not be empty")
    private String email;

    @NotBlank(message = "Name can not be empty")
    private String name;

    private String gravatarUrl;

    @NotBlank(message = "Password can not be empty")
    private String password;

    private Collection<UserRole> userRole = Collections.emptyList();

    private boolean active;

    public void setUserRole(UserRole... userRoles){
        this.userRole = Arrays.asList(userRoles);
    }
}
