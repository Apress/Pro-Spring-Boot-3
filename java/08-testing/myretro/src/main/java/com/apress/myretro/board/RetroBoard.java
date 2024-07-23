package com.apress.myretro.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class RetroBoard {

    @Id
    private UUID id;

    private String name;

    @Singular("card")
    private List<Card> cards;

    public void addCard(Card card){
        if (this.cards == null)
            this.cards = new ArrayList<>();
        this.cards.add(card);
    }

    public void addCards(List<Card> cards){
        if (this.cards == null)
            this.cards = new ArrayList<>();
        this.cards.addAll(cards);
    }


}
