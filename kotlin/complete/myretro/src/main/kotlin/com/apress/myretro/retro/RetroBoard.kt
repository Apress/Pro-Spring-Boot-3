package com.apress.myretro.retro

import com.apress.myretro.user.User
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.HashMap
import java.util.UUID

data class RetroBoard(
    var id: UUID?,
    var name: String?,
    var assignedUsers: HashMap<String, User>? = hashMapOf(),
    var cards: HashMap<UUID, Card>? = hashMapOf(),
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var modifiedAt:LocalDateTime? = LocalDateTime.now()
) {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt:LocalDateTime = LocalDateTime.now()

    constructor():this(UUID.randomUUID(),null, hashMapOf(), hashMapOf(),null)
}