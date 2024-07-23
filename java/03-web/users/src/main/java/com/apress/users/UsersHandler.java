package com.apress.users;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class UsersHandler {

    private final Repository userRepository;
    private final Validator validator;

    public ServerResponse findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.userRepository.findAll());
    }

    public ServerResponse findUserByEmail(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.userRepository.findById(request.pathVariable("email")));
    }

    public ServerResponse save(ServerRequest request) throws ServletException, IOException {
        User user = request.body(User.class);

        BindingResult bindingResult = validate(user);
        if (bindingResult.hasErrors()) {
            return prepareErrorResponse(bindingResult);
        }

        this.userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(user.getEmail())
                .toUri();
        return ServerResponse.created(location).body(user);
    }

    public ServerResponse deleteByEmail(ServerRequest request) {
        this.userRepository.deleteById(request.pathVariable("email"));
        return ServerResponse.noContent().build();
    }

    private BindingResult validate(User user) {
        DataBinder binder = new DataBinder(user);
        binder.addValidators(validator);
        binder.validate();
        return binder.getBindingResult();
    }

    private ServerResponse prepareErrorResponse(BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        response.put("msg","There is an error");
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        response.put("errors",errors);

        return ServerResponse.badRequest().body(response);
    }
}

