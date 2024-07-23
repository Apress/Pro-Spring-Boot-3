package com.apress.users;

import com.apress.users.config.UserConfig;
import com.apress.users.model.User;
import com.apress.users.model.UserRole;
import com.apress.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import({UserConfig.class})
@DataJpaTest
public class UserJpaRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void findAllTest(){
        var expectedUsers = userRepository.findAll();
        assertThat(expectedUsers).isNotEmpty();
        assertThat(expectedUsers).isInstanceOf(Iterable.class);
        assertThat(expectedUsers).element(0).isInstanceOf(User.class);
        assertThat(expectedUsers).element(0).matches( user -> user.isActive());
    }

    @Test
    void saveTest(){
        var dummyUser =  User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build();
        var expectedUser = userRepository.save(dummyUser);
        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser).isInstanceOf(User.class);
        assertThat(expectedUser).hasNoNullFieldsOrProperties();
        assertThat(expectedUser.isActive()).isTrue();
    }

    @Test
    void findByIdTest(){
        var expectedUser = userRepository.findById("norma@email.com");

        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser.get()).isInstanceOf(User.class);
        assertThat(expectedUser.get().isActive()).isTrue();
        assertThat(expectedUser.get().getName()).isEqualTo("Norma");
    }

    @Test
    void deleteByIdTest(){
        var expectedUser = userRepository.findById("ximena@email.com");
        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser.get()).isInstanceOf(User.class);
        assertThat(expectedUser.get().isActive()).isTrue();
        assertThat(expectedUser.get().getName()).isEqualTo("Ximena");

        userRepository.deleteById("ximena@email.com");

        expectedUser = userRepository.findById("ximena@email.com");
        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser).isEmpty();

    }

}
