package com.apress.myretro.board;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {

    @NotNull
    private UUID id;

    @NotBlank
    private String comment;

    @NotNull
    private CardType cardType;

}
