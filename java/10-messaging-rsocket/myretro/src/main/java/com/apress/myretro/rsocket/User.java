package com.apress.myretro.rsocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    UUID id;

    private String email;

    private String name;

    private String gravatarUrl;

    private Collection<UserRole> userRole = Collections.emptyList();

    private boolean active;
}
