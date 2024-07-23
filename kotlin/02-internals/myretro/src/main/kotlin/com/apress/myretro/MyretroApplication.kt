package com.apress.myretro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyretroApplication

fun main(args: Array<String>) {
	runApplication<MyretroApplication>(*args)
}
