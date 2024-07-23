package com.apress.users;

import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class UserRepository implements Repository<User,String>{

    private Map<String,User> users = new HashMap<>() {{
        put("ximena@email.com",User.builder()
                .email("ximena@email.com")
                .name("Ximena")
                .gravatarUrl("https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build());
        put("norma@email.com",User.builder()
                .name("Norma")
                .email("norma@email.com")
                .gravatarUrl("https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .role(UserRole.ADMIN)
                .active(true)
                .build());
    }};

    @Override
    public User save(User user) {
        if (user.getGravatarUrl()==null)
            user.setGravatarUrl("https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar");
        if (user.getUserRole() == null)
            user.setUserRole(Collections.emptyList());

        return this.users.put(user.getEmail(),user);
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.of(this.users.get(id));
    }

    @Override
    public Iterable<User> findAll() {
        return this.users.values();
    }

    @Override
    public void deleteById(String id) {
        this.users.remove(id);
    }
}
