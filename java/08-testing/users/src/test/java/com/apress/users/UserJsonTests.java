package com.apress.users;


import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JsonTest
public class UserJsonTests {

    @Autowired
    private JacksonTester<User> jacksonTester;

    @Test
    void serializeUserJsonTest() throws IOException{
        User user = UserBuilder.createUser(Validation.buildDefaultValidatorFactory().getValidator())
                .withEmail("dummy@email.com")
                .withPassword("aw2s0me")
                .withName("Dummy")
                .withRoles(UserRole.USER)
                .active().build();

        JsonContent<User> json =  jacksonTester.write(user);

        assertThat(json).extractingJsonPathValue("$.email").isEqualTo("dummy@email.com");
        assertThat(json).extractingJsonPathArrayValue("$.userRole").size().isEqualTo(1);
        assertThat(json).extractingJsonPathBooleanValue("$.active").isTrue();
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isNotNull();
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isEqualTo(UserGravatar.getGravatarUrlFromEmail(user.getEmail()));
    }

    @Test
    void serializeUserJsonFileTest() throws IOException{
        User user = UserBuilder.createUser(Validation.buildDefaultValidatorFactory().getValidator())
                .withEmail("dummy@email.com")
                .withPassword("aw2s0me")
                .withName("Dummy")
                .withRoles(UserRole.USER)
                .active().build();

        System.out.println(user);

        JsonContent<User> json =  jacksonTester.write(user);

        // You need to add the user.json file in the src/test/resources/com/apress/users folder
        assertThat(json).isEqualToJson("user.json");

    }

    @Test
    void deserializeUserJsonTest() throws Exception{
        String userJson = """
                {
                  "email": "dummy@email.com",
                  "name": "Dummy",
                  "password": "aw2s0me",
                  "userRole": ["USER"],
                  "active": true
                }
                """;
        User user = this.jacksonTester.parseObject(userJson);

        assertThat(user.getEmail()).isEqualTo("dummy@email.com");
        assertThat(user.getPassword()).isEqualTo("aw2s0me");
        assertThat(user.isActive()).isTrue();

    }

    @Test
    void userValidationTest(){
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy( () -> UserBuilder.createUser(Validation.buildDefaultValidatorFactory().getValidator())
                        .withEmail("dummy@email.com")
                        .withName("Dummy")
                        .withRoles(UserRole.USER)
                        .active().build());

        // Junit 5
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            UserBuilder.createUser(Validation.buildDefaultValidatorFactory().getValidator())
                    .withName("Dummy")
                    .withRoles(UserRole.USER)
                    .active().build();
        });

        String expectedMessage = "email: Email can not be empty";
        assertThat(exception.getMessage()).contains(expectedMessage);

    }
}
