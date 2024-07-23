package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "retros",itemResourceRel = "retros", collectionResourceRel = "retros")
public interface RetroBoardRepository extends JpaRepository<RetroBoard,UUID> {
}
