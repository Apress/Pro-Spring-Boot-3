package com.apress.myretro.client

import com.apress.myretro.user.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class UserClient {

    private val restTemplate: RestTemplate = RestTemplate()
    private val server: String

    constructor(@Value("\${user.server:http://localhost:8080}") server: String) {
        this.server = server
    }

    fun getUserByEmail(email: String): User? {
        val responseEntity = restTemplate.getForEntity(
            "$server/users/$email",
            User::class.java
        )
        return responseEntity.body
    }
}