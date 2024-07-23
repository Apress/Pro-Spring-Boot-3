package com.apress.myretro;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.CardType;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardService;
import com.apress.myretro.web.RetroBoardController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;


@WebFluxTest(controllers = {RetroBoardController.class})
public class RetroBoardWebFluxTests {

    @MockBean
    RetroBoardService retroBoardService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void getAllRetroBoardTest(){

        Mockito.when(retroBoardService.findAll()).thenReturn(Flux.just(
                new RetroBoard(UUID.randomUUID(),"Simple Retro", Arrays.asList(
                        new Card(UUID.randomUUID(),"Happy to be here", CardType.HAPPY),
                        new Card(UUID.randomUUID(),"Meetings everywhere", CardType.SAD),
                        new Card(UUID.randomUUID(),"Vacations?", CardType.MEH),
                        new Card(UUID.randomUUID(),"Awesome Discounts", CardType.HAPPY),
                        new Card(UUID.randomUUID(),"Missed my train", CardType.SAD)
                ))
        ));

        webClient.get()
                .uri("/retros")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$[0].name").isEqualTo("Simple Retro");

        Mockito.verify(retroBoardService,Mockito.times(1)).findAll();
    }

    @Test
    void findRetroBoardByIdTest(){
        UUID uuid = UUID.randomUUID();
        Mockito.when(retroBoardService.findById(uuid)).thenReturn(Mono.just(
                new RetroBoard(uuid,"Simple Retro", Arrays.asList(
                        new Card(UUID.randomUUID(),"Happy to be here", CardType.HAPPY),
                        new Card(UUID.randomUUID(),"Meetings everywhere", CardType.SAD),
                        new Card(UUID.randomUUID(),"Vacations?", CardType.MEH),
                        new Card(UUID.randomUUID(),"Awesome Discounts", CardType.HAPPY),
                        new Card(UUID.randomUUID(),"Missed my train", CardType.SAD)
                ))
        ));

        webClient.get()
                .uri("/retros/{uuid}",uuid.toString())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RetroBoard.class);

        Mockito.verify(retroBoardService,Mockito.times(1)).findById(uuid);
    }

    @Test
    void saveRetroBoardTest(){
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setName("Simple Retro");

        Mockito.when(retroBoardService.save(retroBoard))
                .thenReturn(Mono.just(retroBoard));

        webClient.post()
                .uri("/retros")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(retroBoard))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(retroBoardService,Mockito.times(1)).save(retroBoard);
    }

    @Test
    void deleteRetroBoardTest(){
        UUID uuid = UUID.randomUUID();
        Mockito.when(retroBoardService.delete(uuid)).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/retros/{uuid}",uuid.toString())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(retroBoardService,Mockito.times(1)).delete(uuid);
    }

}
