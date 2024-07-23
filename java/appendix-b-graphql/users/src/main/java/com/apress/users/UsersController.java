package com.apress.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@Controller
public class UsersController {

    private UserRepository userRepository;


    @QueryMapping
    public Iterable<User> users() {
        return this.userRepository.findAll();
    }

    @QueryMapping
    public User user(@Argument String email) throws Throwable {
        return this.userRepository.findById(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @MutationMapping
    public User createUser(@Argument @Valid User user) {
        user.setGravatarUrl(UserGravatar.getGravatarUrlFromEmail(user.getEmail()));
        return userRepository.save(user);
    }

    @MutationMapping
    public User updateUser(@Argument @Valid User user) {
        User userToUpdate = userRepository.findById(user.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        userToUpdate.setName(user.getName());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUserRole(user.getUserRole());
        userToUpdate.setActive(user.isActive());
        return userRepository.save(userToUpdate);
    }

    @MutationMapping
    public boolean deleteUser(@Argument String email) {
        userRepository.deleteById(email);
        return true;
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("msg","There is an error");
        response.put("code",HttpStatus.BAD_REQUEST.value());
        response.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("errors",errors);

        return response;
    }

}



