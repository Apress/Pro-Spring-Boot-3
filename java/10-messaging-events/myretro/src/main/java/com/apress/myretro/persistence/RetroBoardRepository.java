package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RetroBoardRepository extends JpaRepository<RetroBoard,UUID> {

}
