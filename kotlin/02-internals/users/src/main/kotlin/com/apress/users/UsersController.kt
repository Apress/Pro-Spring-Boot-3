package com.apress.users

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController {
    private val users: HashMap<String?, User?> = object : HashMap<String?, User?>() {
        init {
            put("ximena@email.com", User("ximena@email.com", "Ximena"))
            put("norma@email.com", User("norma@email.com", "Norma"))
        }
    }

    @GetMapping
    fun getAll(): MutableCollection<User?> = users.values

    @GetMapping("/{email}")
    fun findUserByEmail(@PathVariable email: String?): User? {
        return users[email]
    }

    @PostMapping
    fun save(@RequestBody user: User): User {
        users[user.email] = user
        return user
    }

    @DeleteMapping("/{email}")
    fun save(@PathVariable email: String?) {
        users.remove(email)
    }
}