package com.apress.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Import({UserConfiguration.class})
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserJpaRepositoryTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

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
        var dummyUser =  UserBuilder.createUser()
                .withName("Dummy")
                .withEmail("dummy@email.com")
                .active()
                .withRoles(UserRole.INFO)
                .withPassword("aw3s0m3R!")
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
