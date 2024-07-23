package com.apress.users.service;

import com.apress.myretro.annotations.MyRetroAudit;
import com.apress.myretro.annotations.MyRetroAuditOutputFormat;
import com.apress.users.actuator.LogEventEndpoint;
import com.apress.users.model.User;
import com.apress.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private ApplicationEventPublisher publisher;
    private LogEventEndpoint logEventsEndpoint;

    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findById(email);
    }

    @MyRetroAudit(showArgs = true, message = "Saving or updating user", format = MyRetroAuditOutputFormat.JSON, prettyPrint = true)
    public User saveUpdateUser(User user) {
        User userResult = this.userRepository.save(user);
        return userResult;
    }

    public void removeUserByEmail(String email) {
        this.userRepository.deleteById(email);
    }
}
