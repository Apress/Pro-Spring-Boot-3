package com.apress.myretro;


import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.persistence.RetroBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@DataMongoTest
public class RetroBoardMongoTests {


    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    RetroBoardRepository retroBoardRepository;

    @Test
    void saveRetroTest(){
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setId(UUID.randomUUID());
        retroBoard.setName("Spring Boot 3 Retro");
        retroBoard.setCards(new java.util.ArrayList<>());

        var retroBoardResult = this.retroBoardRepository.insert(retroBoard).block();

        assertThat(retroBoardResult).isNotNull();
        assertThat(retroBoardResult.getId()).isInstanceOf(UUID.class);
        assertThat(retroBoardResult.getName()).isEqualTo("Spring Boot 3 Retro");
    }

    @Test
    void findRetroBoardById(){
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setId(UUID.randomUUID());
        retroBoard.setName("Migration Retro");
        retroBoard.setCards(new java.util.ArrayList<>());

        var retroBoardResult = this.retroBoardRepository.insert(retroBoard).block();
        assertThat(retroBoardResult).isNotNull();
        assertThat(retroBoardResult.getId()).isInstanceOf(UUID.class);

        Mono<RetroBoard> retroBoardMono = this.retroBoardRepository.findById(retroBoardResult.getId());

        StepVerifier
                .create(retroBoardMono)
                .assertNext( retro -> {
                    assertThat(retro).isNotNull();
                    assertThat(retro).isInstanceOf(RetroBoard.class);
                    assertThat(retro.getName()).isEqualTo("Migration Retro");
                })
                .expectComplete()
                .verify();
    }
}
