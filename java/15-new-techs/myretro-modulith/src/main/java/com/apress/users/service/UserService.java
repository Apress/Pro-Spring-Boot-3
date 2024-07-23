package com.apress.users.service;

import com.apress.users.UserEvent;
import com.apress.users.actuator.LogEventEndpoint;
import com.apress.users.model.User;
import com.apress.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private ApplicationEventPublisher publisher;
    private LogEventEndpoint logEventsEndpoint;
    private ApplicationEventPublisher events;

    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findById(email);
    }

    @Transactional
    public User saveUpdateUser(User user) {
        User userResult = this.userRepository.save(user);

        // Only when the user is saved we do publish the event
        //events.publishEvent(user);


        //Fix - create a UserEvent class at the same level as user package.
        events.publishEvent(new UserEvent(user.getEmail(), "save"));

        return userResult;
    }

    public void removeUserByEmail(String email) {
        this.userRepository.deleteById(email);
    }
}
