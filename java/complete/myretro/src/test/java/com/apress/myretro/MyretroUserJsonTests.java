package com.apress.myretro;

import com.apress.myretro.user.User;
import com.apress.myretro.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MyretroUserJsonTests {

    @Autowired
    private JacksonTester<User> userJson;

    @Test
    void serialize() throws Exception {
        User user = new User("Dummy","dummy@email.com","","dummyP1ssWd", Arrays.asList(UserRole.USER),true);
        JsonContent<User> result =  this.userJson.write(user);
        assertThat(result).hasJsonPathStringValue("$.name");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Dummy");
        assertThat(result).extractingJsonPathStringValue("$.gravatarUrl").isEmpty();
    }

    @Test
    void deserialize() throws Exception {
        String content = "{\"name\":\"Dummy\",\"email\":\"dummy@email.com\",\"gravatarUrl\":\"\",\"password\":\"dummyP1ssWd\",\"userRole\":[\"USER\"],\"active\":true}";
        assertThat(this.userJson.parse(content)).isEqualTo(new User("Dummy","dummy@email.com","","dummyP1ssWd", Arrays.asList(UserRole.USER),true));
        assertThat(this.userJson.parseObject(content).getEmail()).isEqualTo("dummy@email.com");
    }
}
