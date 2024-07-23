package com.apress.myretro.board;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Card {

    private UUID id;

    @NotBlank(message = "A comment must be provided always")
    @NotNull
    private String comment;

    @NotNull(message = "A CarType HAPPY|MEH|SAD must be provided")
    private CardType cardType;
}
