package com.apress.myretro.persistence;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;

import java.util.*;

public class RetroBoardRepository implements Repository<RetroBoard, UUID> {

    Map<UUID, RetroBoard> boards = new HashMap<>() {{
        put(UUID.fromString("66D4A370-C312-4426-9C39-B411D0E43DAB"), new RetroBoard(
                UUID.randomUUID(), "Demo 1", Arrays.asList(
                new Card("This is awesome", CardType.HAPPY),
                new Card("I wondering when to meet again", CardType.MEH),
                new Card("Not enough timee", CardType.SAD)
        )
        ));
    }};


    @Override
    public RetroBoard save(RetroBoard domain) {
        UUID uuid = UUID.randomUUID();
        if (domain.getUuid() == null)
            domain.setUuid(uuid);
        return this.boards.put(domain.getUuid(),domain);
    }

    @Override
    public Optional<RetroBoard> findById(UUID uuid) {
        return Optional.of(this.boards.get(uuid));
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
