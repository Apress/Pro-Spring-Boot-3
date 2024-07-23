package com.apress.myretro.service;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.exception.CardNotFoundException;
import com.apress.myretro.exception.RetroBoardNotFoundException;
import com.apress.myretro.persistence.RetroBoardRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    public Card addCardToRetroBoard(UUID uuid, Card card) {
        RetroBoard retroBoard = retroBoardRepository.findById(uuid).get();

        List<Card> cards = retroBoard.getCards();
        if (cards == null) {
            cards = new ArrayList<>();
        }
        if (card.getId() == null)
            card.setId(UUID.randomUUID());

        cards.add(card);
        retroBoard.setCards(cards);
        retroBoardRepository.save(retroBoard);
        return card;
    }

    public Card findCardByUUID(UUID uuid, UUID uuidCard) {
        Helper<RetroBoard, Card> helper = findRetroBoardAndCard(uuid, uuidCard);
        return helper.getU();
    }

    public Card saveCard(UUID uuid, Card card) {
        Helper<RetroBoard, Card> helper = findRetroBoardAndCard(uuid, card.getId());
        RetroBoard retroBoard = helper.getT();
        Card cardToSave = helper.getU();
        cardToSave.setComment(card.getComment());
        cardToSave.setCardType(card.getCardType());
        retroBoardRepository.save(retroBoard);
        return cardToSave;

    }

    public void removeCardByUUID(UUID uuid, UUID cardUUID) {
        Helper<RetroBoard, Card> helper = findRetroBoardAndCard(uuid, cardUUID);
        helper.getT().getCards().remove(helper.getU());
        retroBoardRepository.save(helper.getT());
    }

    @AllArgsConstructor
    @Data
    private class Helper<T, U> {
        private T t;
        private U u;
    }

    private Helper<RetroBoard, Card> findRetroBoardAndCard(UUID uuid, UUID cardUUID) {
        RetroBoard retroBoard = retroBoardRepository.findById(uuid).get();
        List<Card> cards = retroBoard.getCards();
        if (cards == null) {
            throw new CardNotFoundException();
        }
        Optional<Card> cardOptional = cards.stream().filter(c -> c.getId().equals(cardUUID)).findFirst();
        if (cardOptional.isPresent()) {
            return new Helper<>(retroBoard, cardOptional.get());
        } else {
            throw new CardNotFoundException();
        }
    }

}
