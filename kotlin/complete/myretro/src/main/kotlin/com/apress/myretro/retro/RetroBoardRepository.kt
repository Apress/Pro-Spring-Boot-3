package com.apress.myretro.retro

import com.apress.myretro.persistence.Repository
import com.apress.myretro.user.User
import com.apress.myretro.user.UserRole
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

@Component
class RetroBoardRepository : Repository <RetroBoard,UUID> {
    var boards: MutableMap<UUID?,RetroBoard> =  mutableMapOf<UUID?,RetroBoard>(
        UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de") to
                RetroBoard(
                    UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"),
                    "Uno",
                    hashMapOf( "emilio@email.com" to User("Emilio","emilio@email.com","https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar","aw2s0me", listOf(
                        UserRole.USER),true)),
                    hashMapOf(),
                    LocalDateTime.now()
                ),
        UUID.fromString("f73c6f55-95d9-438c-9df4-e17b123943d8") to
                RetroBoard(
                    UUID.fromString("f73c6f55-95d9-438c-9df4-e17b123943d8"),
                    "Dos",
                    hashMapOf(),
                    hashMapOf(),
                    LocalDateTime.now()
                )
    )

    override fun save(domain: RetroBoard): RetroBoard {
        if (null == domain.id) {
            val uuid = UUID.randomUUID()
            domain.id = uuid
            boards[uuid] = domain
            return domain
        }

        val updatedBoard = boards[domain.id]
        updatedBoard?.name = domain.name
        updatedBoard?.cards = domain.cards
        updatedBoard?.assignedUsers = domain.assignedUsers
        updatedBoard?.modifiedAt = LocalDateTime.now()
        boards[updatedBoard?.id] = updatedBoard!!
        return updatedBoard!!
    }

    override fun findById(id: UUID): RetroBoard? = this.boards[id]

    override fun findAll(): Iterable<RetroBoard> = this.boards.values

    override fun delete(id: UUID) {
        this.boards.remove(id)
    }
}
