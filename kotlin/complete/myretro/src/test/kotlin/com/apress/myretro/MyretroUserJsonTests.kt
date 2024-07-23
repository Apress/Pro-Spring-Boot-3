package com.apress.myretro

import com.apress.myretro.user.User
import com.apress.myretro.user.UserRole
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.util.*

@JsonTest
class MyretroUserJsonTests {

    @Autowired
    private val userJson: JacksonTester<User>? = null

    @Test
    @Throws(Exception::class)
    fun serialize() {
        val user = User("Dummy", "dummy@email.com", "", "dummyP1ssWd", listOf(UserRole.USER), true)
        val result = userJson!!.write(user)
        Assertions.assertThat(result).hasJsonPathStringValue("$.name")
        Assertions.assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Dummy")
        Assertions.assertThat(result).extractingJsonPathStringValue("$.gravatarUrl").isEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun deserialize() {
        val content =
            "{\"name\":\"Dummy\",\"email\":\"dummy@email.com\",\"gravatarUrl\":\"\",\"password\":\"dummyP1ssWd\",\"userRole\":[\"USER\"],\"active\":true}"
        Assertions.assertThat(userJson!!.parse(content))
            .isEqualTo(User("Dummy", "dummy@email.com", "", "dummyP1ssWd", listOf(UserRole.USER), true))
        Assertions.assertThat(userJson.parseObject(content).email).isEqualTo("dummy@email.com")
    }
}