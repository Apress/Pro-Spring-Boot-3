package com.apress.users

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UsersSmokeTests {

    @Autowired
    private val controller: UsersController? = null

	@Test
	fun contextLoads() {
        Assertions.assertThat(controller).isNotNull
	}

}
