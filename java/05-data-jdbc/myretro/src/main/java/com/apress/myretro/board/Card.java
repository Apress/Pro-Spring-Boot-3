package com.apress.myretro.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class Card {

    @Id
    private UUID id;

    @NotBlank
    private String comment;

    @NotNull
    private CardType cardType;

    @JsonIgnore
    private UUID retroBoardId;
}
