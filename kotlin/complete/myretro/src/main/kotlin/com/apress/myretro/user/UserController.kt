package com.apress.myretro.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (val userRepository: UserRepository) {

    @GetMapping
    fun getAllUsers():Iterable<User> = userRepository.findAll()

    @GetMapping("/{email}")
    fun getUserByEmail(@PathVariable email:String): User? = userRepository.findById(email)

    @PostMapping
    fun addNewUser(@RequestBody user: User): User = userRepository.save(user)

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable email:String) = userRepository.delete(email)
}