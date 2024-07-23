package com.apress.users.service;


import com.apress.users.model.User;
import com.apress.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;


    public Flux<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public Mono<User> findUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public Mono<User> saveUpdateUser(User user){
        Mono<User> userResult = this.userRepository.save(user);
        return userResult;
    }

    public void removeUserByEmail(String email){
        this.userRepository.deleteByEmail(email);
    }
}
