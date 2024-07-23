package com.apress.users;


import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;
import java.util.UUID;


@Table("PEOPLE")
public record User(

        @Id
        UUID id,

        @NotBlank(message = "Email can not be empty")
        String email,

        @NotBlank(message = "Name can not be empty")
        String name,

        String gravatarUrl,

        @NotBlank(message = "Password can not be empty")
        String password,

        Collection<UserRole> userRole,

        boolean active
) {

    public User withGravatarUrl(String email) {
        String url = UserGravatar.getGravatarUrlFromEmail(email);
        return new User(UUID.randomUUID(), email, name, url, password, userRole, active);
    }

}
