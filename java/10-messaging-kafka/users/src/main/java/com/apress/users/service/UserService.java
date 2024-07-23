package com.apress.users.service;


import com.apress.users.events.UserActivatedEvent;
import com.apress.users.events.UserRemovedEvent;
import com.apress.users.model.User;
import com.apress.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private ApplicationEventPublisher publisher;

    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email){
        return this.userRepository.findById(email);
    }

    public User saveUpdateUser(User user){
        User userResult = this.userRepository.save(user);
        this.publisher.publishEvent(new UserActivatedEvent(userResult.getEmail(),userResult.isActive()));
        return userResult;
    }

    public void removeUserByEmail(String email){
        this.userRepository.deleteById(email);
        this.publisher.publishEvent(new UserRemovedEvent(email, LocalDateTime.now()));
    }
}
