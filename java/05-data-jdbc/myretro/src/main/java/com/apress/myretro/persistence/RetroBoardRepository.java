package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RetroBoardRepository extends CrudRepository<RetroBoard,UUID> {
}
