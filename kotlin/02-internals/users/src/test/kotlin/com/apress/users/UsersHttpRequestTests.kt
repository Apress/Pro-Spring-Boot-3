package com.apress.users

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersHttpRequestTests {
    @Value("\${local.server.port}")
    private val port = 0
    private val BASE_URL = "http://localhost:"
    private val USERS_PATH = "/users"

    @Autowired
    private val restTemplate: TestRestTemplate? = null

    @Test
    @Throws(Exception::class)
    fun indexPageShouldReturnHeaderOneContent() {
        Assertions.assertThat(
            restTemplate!!.getForObject(
                BASE_URL + port,
                String::class.java
            )
        ).contains("Simple Users Rest Application")
    }

    @Test
    @Throws(Exception::class)
    fun usersEndPointShouldReturnCollectionWithTwoUsers() {
        val response: MutableCollection<User> = restTemplate!!.getForObject(
            BASE_URL + port + USERS_PATH,
            MutableCollection::class.java
        ) as MutableCollection<User>
        Assertions.assertThat(response.size).isEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnUser() {
        val user = User("dummy@email.com", "Dummy")
        val response = restTemplate!!.postForObject(
            BASE_URL + port + USERS_PATH, user,
            User::class.java
        )
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.email).isEqualTo(user.email)
        val users: MutableCollection<User> = restTemplate!!.getForObject(
            BASE_URL + port + USERS_PATH,
            MutableCollection::class.java
        ) as MutableCollection<User>
        Assertions.assertThat(users.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointDeleteUserShouldReturnVoid() {
        restTemplate!!.delete("$BASE_URL$port$USERS_PATH/norma@email.com")
        val users: MutableCollection<User> = restTemplate!!.getForObject(
            BASE_URL + port + USERS_PATH,
            MutableCollection::class.java
        ) as MutableCollection<User>
        Assertions.assertThat(users.size).isLessThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointFindUserShouldReturnUser() {
        val user = restTemplate!!.getForObject(
            "$BASE_URL$port$USERS_PATH/ximena@email.com",
            User::class.java
        )
        Assertions.assertThat(user).isNotNull
        Assertions.assertThat(user.email).isEqualTo("ximena@email.com")
    }
}