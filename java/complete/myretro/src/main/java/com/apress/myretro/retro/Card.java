package com.apress.myretro.retro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {

    private UUID id;

    private CardType cardType;

    private String comment;

    private String userEmail;
}
