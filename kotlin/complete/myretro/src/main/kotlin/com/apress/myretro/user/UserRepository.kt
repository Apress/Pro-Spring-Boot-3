package com.apress.myretro.user

import com.apress.myretro.persistence.Repository
import com.apress.myretro.utils.GravatarImage
import org.springframework.stereotype.Component

@Component
class UserRepository : Repository<User, String> {
    val people =  mutableMapOf<String, User>(
        "ximena@email.com" to User("Ximena","ximena@email.com","https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar","aw2s0me", listOf(UserRole.USER),true),
        "norma@email.com" to User("Norma","norma@email.com","https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar","aw2s0me", listOf(UserRole.USER, UserRole.ADMIN), true)
    )

    override fun save(domain: User): User {
        domain.gravatarUrl = GravatarImage.getGravatarUrlFromEmail(domain.email)
        this.people += domain.email to domain
        return domain
    }

    override fun findById(id: String): User? = this.people[id]

    override fun findAll(): Iterable<User> = this.people.values

    override fun delete(id: String) {
        this.people.remove(id)
    }
}