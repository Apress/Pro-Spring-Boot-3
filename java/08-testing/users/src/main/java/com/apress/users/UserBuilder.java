package com.apress.users;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

public class UserBuilder {

    private static UserBuilder instance;
    private static User user;
    private static Validator validator;

    private UserBuilder(){
    }

    public static UserBuilder createUser(Validator v){
        validator = v;
        return createUser();
    }

    public static UserBuilder createUser(){
        user = new User();
        instance = new UserBuilder();
        return instance;
    }

    public UserBuilder withEmail(String email){
        user.setEmail(email);
        return instance;
    }

    public UserBuilder withPassword(String password){
        user.setPassword(password);
        return instance;
    }

    public UserBuilder withName(String name){
        user.setName(name);
        return instance;
    }

    public UserBuilder withRoles(UserRole... userRoles){
        user.setUserRole(userRoles);
        return instance;
    }

    public UserBuilder active(){
        user.setActive(true);
        return instance;
    }

    public UserBuilder inactive(){
        user.setActive(false);
        return instance;
    }

    public User build(){
        if (validator != null) {
            Set<ConstraintViolation<User>> constraintViolation = validator.validate(user);
            if (!constraintViolation.isEmpty()) {
                throw new ConstraintViolationException(constraintViolation);
            }
        }
        user.setGravatarUrl(UserGravatar.getGravatarUrlFromEmail(user.getEmail()));
        return user;
    }
}
