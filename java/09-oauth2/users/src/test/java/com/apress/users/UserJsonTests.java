package com.apress.users;


import com.apress.users.model.User;
import com.apress.users.model.UserGravatar;
import com.apress.users.model.UserRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserJsonTests {

    @Autowired
    private JacksonTester<User> jacksonTester;

    @Test
    void serializeUserJsonTest() throws IOException{
        User user = User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build();

        JsonContent<User> json =  jacksonTester.write(user);

        assertThat(json).extractingJsonPathValue("$.email").isEqualTo("dummy@email.com");
        assertThat(json).extractingJsonPathArrayValue("$.userRole").size().isEqualTo(1);
        assertThat(json).extractingJsonPathBooleanValue("$.active").isTrue();
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isNotNull();
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isEqualTo(UserGravatar.getGravatarUrlFromEmail(user.getEmail()));
    }

    @Test
    void serializeUserJsonFileTest() throws IOException{
        User user = User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build();

        System.out.println(user);

        JsonContent<User> json =  jacksonTester.write(user);

        assertThat(json).isEqualToJson("user.json");

    }

    @Test
    void deserializeUserJsonTest() throws Exception{
        String userJson = """
                {
                  "email": "dummy@email.com",
                  "name": "Dummy",
                  "password": "aw2s0meR!",
                  "userRole": ["USER"],
                  "gravatarUrl": "https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                  "active": true
                }
                """;
        User user = this.jacksonTester.parseObject(userJson);

        assertThat(user.getEmail()).isEqualTo("dummy@email.com");
        assertThat(user.getPassword()).isEqualTo("aw2s0meR!");
        assertThat(user.isActive()).isTrue();

    }

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void userValidationTest(){
        User user1 = User.builder()
                .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                .password("aw2s")
                .role(UserRole.USER)
                .active(true)
                .build();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user1);
        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).hasSize(5);
    }

}
