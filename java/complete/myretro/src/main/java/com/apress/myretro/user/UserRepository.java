package com.apress.myretro.user;

import com.apress.myretro.persistence.Repository;
import com.apress.myretro.utils.GravatarImage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserRepository implements Repository<User,String> {
    private Map<String,User> people = new HashMap<>() {{
        put("ximena@email.com",new User("Ximena","ximena@email.com","https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar","aw2s0me", Arrays.asList(UserRole.USER),true));
        put("norma@email.com",new User("Norma", "norma@email.com","https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar", "aw2s0me", Arrays.asList(UserRole.USER, UserRole.ADMIN),true));
    }};
    @Override
    public User save(User domain) {
        domain.setGravatarUrl(GravatarImage.getGravatarUrlFromEmail(domain.getEmail()));
        return this.people.put(domain.getEmail(),domain);
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(this.people.get(id));
    }

    @Override
    public Iterable<User> findAll() {
        return this.people.values();
    }

    @Override
    public void delete(String id) {
        this.people.remove(id);
    }
}
