package com.apress.users;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {
    private Map<String,User> users = new HashMap<>() {{
        put("ximena@email.com",new User("ximena@email.com","Ximena"));
        put("norma@email.com",new User("norma@email.com","Norma"));

    }};

    @GetMapping
    public Collection<User> getAll(){
        return this.users.values();
    }

    @GetMapping("/{email}")
    public User findUserByEmail(@PathVariable String email){
        return this.users.get(email);
    }

    @PostMapping
    public User save(@RequestBody User user){
        this.users.put(user.getEmail(),user);
        return user;
    }

    @DeleteMapping("/{email}")
    public void save(@PathVariable String email){
        this.users.remove(email);
    }
}
