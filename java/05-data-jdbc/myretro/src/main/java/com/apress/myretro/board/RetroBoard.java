package com.apress.myretro.board;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class RetroBoard {

    @Id
    private UUID id;

    @NotBlank(message = "A name must be provided")
    private String name;

    @MappedCollection(idColumn = "retro_board_id",keyColumn = "id")
    private Map<UUID,Card> cards = new HashMap<>();
}
