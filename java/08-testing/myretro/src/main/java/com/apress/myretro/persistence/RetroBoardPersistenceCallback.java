package com.apress.myretro.persistence;

import com.apress.myretro.board.RetroBoard;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RetroBoardPersistenceCallback implements ReactiveBeforeConvertCallback<RetroBoard> {

    @Override
    public Publisher<RetroBoard> onBeforeConvert(RetroBoard entity, String collection) {
        if (entity.getId() == null)
            entity.setId(java.util.UUID.randomUUID());
        if (entity.getCards() == null)
            entity.setCards(new java.util.ArrayList<>());
        log.info("[CALLBACK] onBeforeConvert {}", entity);
        return Mono.just(entity);
    }
}