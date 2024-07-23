package com.apress.users;

import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public record User(Integer id, String email, String name, String password, boolean active, String gravatarUrl, @Singular("role") List<UserRole> userRole) {
    public User {
        Objects.requireNonNull(email);
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);

        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches())
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one number, one uppercase, one lowercase and one special character");

        pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new IllegalArgumentException("Email must be a valid email address");

        if (gravatarUrl == null) {
            gravatarUrl = UserGravatar.getGravatarUrlFromEmail(email);
        }

        if (userRole == null) {
            userRole = new ArrayList<>(){{ add(UserRole.INFO); }};
        }
    }

    public User withId(Integer id){
        return new User(id, this.email(), this.name(), this.password(), this.active(), this.gravatarUrl(), this.userRole());
    }
}


/*
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    Long id;

    @NotBlank(message = "Email can not be empty")
    private String email;

    @NotBlank(message = "Name can not be empty")
    private String name;

    private String gravatarUrl;

    @Pattern(message = "Password must be at least 8 characters long and contain at least one number, one uppercase, one lowercase and one special character",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    private String password;

    @Singular("role")
    private List<UserRole> userRole;

    private boolean active;
}
*/
