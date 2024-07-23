package com.apress.myretro.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RetroBoard {

    private UUID uuid;

    private String name;

    private List<Card> cards = new ArrayList<>();


}
