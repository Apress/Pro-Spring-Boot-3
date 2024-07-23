package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.exception.RetroBoardNotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RetroBoardRepository extends MongoRepository<RetroBoard,UUID> {

    @Query("{'id': ?0}")
    Optional<RetroBoard> findById(UUID id);

    @Query("{}, { cards: { $elemMatch: { _id: ?0 } } }")
    Optional<RetroBoard> findRetroBoardByIdAndCardId(UUID cardId);

    default void removeCardFromRetroBoard(UUID retroBoardId, UUID cardId) {
        Optional<RetroBoard> retroBoard = findById(retroBoardId);
        if (retroBoard.isPresent()) {
            retroBoard.get().getCards().removeIf(card -> card.getId().equals(cardId));
            save(retroBoard.get());
        }else {
            throw new RetroBoardNotFoundException();
        }
    }

}
