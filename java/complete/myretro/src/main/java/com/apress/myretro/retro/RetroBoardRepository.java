package com.apress.myretro.retro;

import com.apress.myretro.persistence.Repository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class RetroBoardRepository implements Repository<RetroBoard, UUID> {

    private Map<UUID,RetroBoard> boards = new HashMap<>() {{
        put(UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"),
                new RetroBoard(UUID.fromString("b193280f-7083-48f9-89b0-6826d04cb4de"), "Uno", new HashMap<>(), new HashMap<>(), LocalDateTime.now()));
        put(UUID.fromString("f73c6f55-95d9-438c-9df4-e17b123943d8"),
                new RetroBoard(UUID.fromString("f73c6f55-95d9-438c-9df4-e17b123943d8")," Dos", new HashMap<>(), new HashMap<>(),LocalDateTime.now()));
    }};

    @Override
    public RetroBoard save(RetroBoard domain) {

        if (null == domain.getId()) {
            domain.setId(UUID.randomUUID());
            this.boards.put(domain.getId(), domain);
            return domain;
        }

        RetroBoard updatedBoard = this.boards.get(domain.getId());
        updatedBoard.setName(domain.getName());
        updatedBoard.setCards(domain.getCards());
        updatedBoard.setAssignedUsers(domain.getAssignedUsers());
        updatedBoard.setModifiedAt(LocalDateTime.now());
        this.boards.put(updatedBoard.getId(),updatedBoard);
        return updatedBoard;
    }

    @Override
    public Optional<RetroBoard> findById(UUID uuid) {
        return Optional.ofNullable(this.boards.get(uuid));
    }

    @Override
    public Iterable<RetroBoard> findAll() {
        return this.boards.values();
    }

    @Override
    public void delete(UUID uuid) {
        this.boards.remove(uuid);
    }
}
