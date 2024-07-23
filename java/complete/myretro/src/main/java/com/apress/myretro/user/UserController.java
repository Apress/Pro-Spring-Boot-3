package com.apress.myretro.user;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    @GetMapping
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{email}")
    User getUserByEmail(@PathVariable String email){
        return userRepository.findById(email).get();
    }

    @PostMapping
    User addNewUser(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable String email){
        userRepository.delete(email);
    }
}
