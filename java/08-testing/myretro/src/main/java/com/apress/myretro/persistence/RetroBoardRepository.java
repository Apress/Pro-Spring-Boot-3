package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RetroBoardRepository extends ReactiveMongoRepository<RetroBoard,UUID> {

    @Query("{'id': ?0}")
    Mono<RetroBoard> findById(UUID id);

    @Query("{}, { cards: { $elemMatch: { _id: ?0 } } }")
    Mono<RetroBoard> findRetroBoardByIdAndCardId(UUID cardId);

    default Mono<Void> removeCardFromRetroBoard(UUID retroBoardId, UUID cardId) {
        findById(retroBoardId)
                .doOnNext(retroBoard -> retroBoard.getCards().removeIf(card -> card.getId().equals(cardId)))
                .flatMap(this::save)
                .subscribe();
        return Mono.empty();
    }

}
