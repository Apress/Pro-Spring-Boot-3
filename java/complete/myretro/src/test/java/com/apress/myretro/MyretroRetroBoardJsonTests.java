package com.apress.myretro;

import com.apress.myretro.retro.RetroBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MyretroRetroBoardJsonTests {

    @Autowired
    private JacksonTester<RetroBoard> retroBoardJson;

    @Test
    void serialize() throws Exception {
        RetroBoard retroBoard = new RetroBoard(UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"), "Uno", new HashMap<>(), new HashMap<>(), LocalDateTime.now());
        JsonContent<RetroBoard> result =  this.retroBoardJson.write(retroBoard);
        assertThat(result).hasJsonPathStringValue("$.name");
        assertThat(result).hasJsonPathStringValue("$.id");
        assertThat(result).hasJsonPathValue("$.assignedUsers");
        assertThat(result).extractingJsonPathStringValue("$.id").isEqualTo("b193280f-7083-48f9-89b0-6826d04cb4de");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Uno");
        assertThat(result).extractingJsonPathMapValue("$.assignedUsers").isEmpty();
    }

    @Test
    void deserialize() throws Exception {
        String content = """
                 {
                  "id": "b193280f-7083-48f9-89b0-6826d04cb4de",
                  "name": "Uno",
                  "assignedUsers": { },
                  "cards": {},
                  "createdAt": "2023-01-30 15:56:33",
                  "modifiedAt": "2023-01-30 15:56:33"
                 }
                """;
        RetroBoard retroBoard = new RetroBoard(UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"), "Uno", new HashMap<>(), new HashMap<>(),LocalDateTime.now());
        assertThat(this.retroBoardJson.parseObject(content).getId()).isEqualTo(UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"));
        assertThat(this.retroBoardJson.parseObject(content).getName()).isEqualTo("Uno");

    }
}
