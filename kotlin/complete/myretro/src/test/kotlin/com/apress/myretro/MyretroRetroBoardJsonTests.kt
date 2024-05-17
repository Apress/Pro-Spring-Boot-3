package com.apress.myretro

import com.apress.myretro.retro.RetroBoard
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.time.LocalDateTime
import java.util.*

@JsonTest
class MyretroRetroBoardJsonTests {

    @Autowired
    private val retroBoardJson: JacksonTester<RetroBoard>? = null

    @Test
    @Throws(Exception::class)
    fun serialize() {
        val retroBoard = RetroBoard(
            UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"),
            "Uno",
            hashMapOf(),
            hashMapOf(),
            LocalDateTime.now()
        )
        val result = retroBoardJson!!.write(retroBoard)
        Assertions.assertThat(result).hasJsonPathStringValue("$.name")
        Assertions.assertThat(result).hasJsonPathStringValue("$.id")
        Assertions.assertThat(result).hasJsonPathValue("$.assignedUsers")
        Assertions.assertThat(result).extractingJsonPathStringValue("$.id")
            .isEqualTo("b193280f-7083-48f9-89b0-6826d04cb4de")
        Assertions.assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Uno")
        Assertions.assertThat(result).extractingJsonPathMapValue<Any, Any>("$.assignedUsers").isEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun deserialize() {
        val content = """
                {
                    "id": "b193280f-7083-48f9-89b0-6826d04cb4de",
                    "name": "Uno",
                    "assignedUsers": { },
                    "cards": {},
                    "createdAt": "2023-01-30 15:56:33",
                    "modifiedAt": "2023-01-30 15:56:33"
                }
                
                """.trimIndent()
        val (id, name, assignedUsers, cards) = RetroBoard(
            UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"),
            "Uno",
            hashMapOf(),
            hashMapOf(),
            LocalDateTime.now()
        )
        Assertions.assertThat(retroBoardJson!!.parseObject(content).id)
            .isEqualTo(UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"))
        Assertions.assertThat(retroBoardJson.parseObject(content).name).isEqualTo("Uno")
    }
}