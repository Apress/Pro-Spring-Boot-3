package com.apress.myretro.service;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.exception.CardNotFoundException;
import com.apress.myretro.exception.RetroBoardNotFoundException;
import com.apress.myretro.persistence.CardRepository;
import com.apress.myretro.persistence.RetroBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RetroBoardService {

    RetroBoardRepository retroBoardRepository;
    CardRepository cardRepository;

    public RetroBoard save(RetroBoard domain) {
        return this.retroBoardRepository.save(domain);
    }

    public RetroBoard findById(UUID uuid) {
        return this.retroBoardRepository.findById(uuid).get();
    }

    public Iterable<RetroBoard> findAll() {
        return this.retroBoardRepository.findAll();
    }

    public void delete(UUID uuid) {
        this.retroBoardRepository.deleteById(uuid);
    }

    public Iterable<Card> findAllCardsFromRetroBoard(UUID uuid) {
        return this.findById(uuid).getCards();
    }

    public Card addCardToRetroBoard(UUID uuid, Card card){
        Card result = retroBoardRepository.findById(uuid).map(retroBoard -> {
            card.setRetroBoard(retroBoard);
            return cardRepository.save(card);
        }).orElseThrow(() -> new RetroBoardNotFoundException());
        return result;
    }

    public void addMultipleCardsToRetroBoard(UUID uuid, List<Card> cards){
        RetroBoard retroBoard = this.findById(uuid);
        cards.forEach(card -> card.setRetroBoard(retroBoard));
        cardRepository.saveAll(cards);
    }

    public Card findCardByUUID(UUID uuidCard){
        Optional<Card> result = cardRepository.findById(uuidCard);
        if(result.isPresent()){
            return result.get();
        }else{
            throw new CardNotFoundException();
        }
    }

    public Card saveCard(Card card){
        return cardRepository.save(card);
    }

    public void removeCardByUUID(UUID cardUUID){
        cardRepository.deleteById(cardUUID);
    }
}
