package com.apress.myretro.client.model;

import java.util.Collection;

public record User (String email, String name, String gravatarUrl, String password, Collection<UserRole> userRole, boolean active)
{}
