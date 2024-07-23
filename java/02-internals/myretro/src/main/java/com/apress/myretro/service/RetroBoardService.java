package com.apress.myretro.service;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.persistence.Repository;

import java.util.UUID;

public class RetroBoardService {

    private Repository repository;

    public RetroBoardService(Repository repository){
        this.repository =  repository;
    }

    public Iterable<Card> findAllHappyCardsFromRetroBoardId(UUID uuid){
        RetroBoard retroBoard = (RetroBoard) this.repository.findById(uuid).get();
        return retroBoard.getCards().stream().filter( card -> card.getCardType() == CardType.HAPPY).toList();
    }
}
