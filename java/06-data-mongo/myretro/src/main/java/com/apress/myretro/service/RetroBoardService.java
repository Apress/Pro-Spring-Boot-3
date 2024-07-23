package com.apress.myretro.service;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.exception.CardNotFoundException;
import com.apress.myretro.persistence.RetroBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RetroBoardService {

    RetroBoardRepository retroBoardRepository;

    public RetroBoard save(RetroBoard domain) {
        if (domain.getCards() == null)
            domain.setCards(new ArrayList<>());
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
        if (card.getId() == null)
            card.setId(UUID.randomUUID());
        RetroBoard retroBoard = this.findById(uuid);
        retroBoard.addCard(card);
        retroBoardRepository.save(retroBoard);
        return card;
    }

    public void addMultipleCardsToRetroBoard(UUID uuid, List<Card> cards){
        RetroBoard retroBoard = this.findById(uuid);
        retroBoard.addCards(cards);
        retroBoardRepository.save(retroBoard);
    }

    public Card findCardByUUID(UUID uuidCard){
        Optional<RetroBoard> result = retroBoardRepository.findRetroBoardByIdAndCardId(uuidCard);
        if(result.isPresent() && result.get().getCards().size() > 0
                && result.get().getCards().get(0).getId().equals(uuidCard)){
            return result.get().getCards().get(0);
        }else{
            throw new CardNotFoundException();
        }
    }

    public void removeCardByUUID(UUID uuid,UUID cardUUID){
        retroBoardRepository.removeCardFromRetroBoard(uuid,cardUUID);
    }
}
