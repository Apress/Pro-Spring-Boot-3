package com.apress.myretro.retro;

import com.apress.myretro.client.UserClient;
import com.apress.myretro.persistence.Repository;
import com.apress.myretro.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RetroBoardService {
    private Repository<RetroBoard, UUID> repository;
    private UserClient userClient;

    public Iterable<RetroBoard> getAllAvailableBoars(){
        return this.repository.findAll();
    }

    RetroBoard saveBoard(RetroBoard retroBoard){
        return this.repository.save(retroBoard);
    }

    Optional<RetroBoard> findBoardById(String boardId){
        return this.repository.findById(UUID.fromString(boardId));
    }

    void deleteBoardById(String boardId){
        this.repository.delete(UUID.fromString(boardId));
    }

    Iterable<Card> getCardsFromBoardId(String boardId){
        RetroBoard board = findBoardById(boardId).get();
        if (null == board) return null;
        return board.getCards().values();
    }

    void removeCardFromBoard(String cardId, String boardId){
        RetroBoard board = findBoardById(boardId).get();
        if (null == board) return;
        board.getCards().remove(UUID.fromString(cardId));
    }

    Card saveCardTo(String boardId, Card card){
        RetroBoard board = findBoardById(boardId).get();
        if (null == board) return null;


        if (null == card.getId()){
            card.setId(UUID.randomUUID());
            board.getCards().put(card.getId(),card);
            return card;
        }

        Card updatedCard = board.getCards().get(card.getId());
        updatedCard.setComment(card.getComment());
        return updatedCard;
    }

    User assignUserToBoard(String boardId, String email){
        RetroBoard board = findBoardById(boardId).get();
        if (null == board) return null;
        User user = this.userClient.getUserByEmail(email);

        if (null != user) board.getAssignedUsers().put(user.getEmail(),user);

        return user;
    }

    // New
    Iterable<Card> getHappyCardsFromRetroBoardId(String boardId) {
        return getCardsFromBoardIdAndCardType(boardId,CardType.HAPPY);
    }

    Iterable<Card> getMehCardsFromRetroBoardId(String boardId) {
        return getCardsFromBoardIdAndCardType(boardId,CardType.MEH);
    }

    Iterable<Card> getSadCardsFromRetroBoardId(String boardId) {
        return getCardsFromBoardIdAndCardType(boardId,CardType.SAD);
    }

    private Iterable<Card> getCardsFromBoardIdAndCardType(String boardId, CardType cardType){
        RetroBoard board = findBoardById(boardId).get();
        if (null == board) return null;
        return board.getCards().values().stream().filter(card -> card.getCardType() == cardType).toList();
    }

    void removeUserFromBoard(String boardId,String email){
        this.findBoardById(boardId).get().getAssignedUsers().remove(email);
    }
}
