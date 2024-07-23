package com.apress.myretro.board;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RetroBoard {

    private UUID id;

    @NotBlank(message = "A name must be provided")
    private String name;

    @Singular
    private Map<UUID,Card> cards = new HashMap<>();


}
