package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface RetroBoardRepository extends ReactiveMongoRepository<RetroBoard,UUID> {

}
