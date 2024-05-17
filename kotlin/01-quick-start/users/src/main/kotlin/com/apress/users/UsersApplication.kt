package com.apress.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UsersApplication

fun main(args: Array<String>) {
	runApplication<UsersApplication>(*args)
}
