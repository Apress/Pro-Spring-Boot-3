package com.apress.myretro.web;

import com.apress.myretro.board.Card;
import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.service.RetroBoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/retros")
public class RetroBoardController {

    private RetroBoardService retroBoardService;

    @GetMapping
    public Flux<RetroBoard> getAllRetroBoards() {
        return retroBoardService.findAll();
    }

    @PostMapping
    public Mono<RetroBoard> saveRetroBoard(@RequestBody RetroBoard retroBoard) {
        return retroBoardService.save(retroBoard);
    }

    @GetMapping("/{uuid}")
    public Mono<RetroBoard> findRetroBoardById(@PathVariable UUID uuid) {
        return retroBoardService.findById(uuid);
    }

    @GetMapping("/{uuid}/cards")
    public Flux<Card> getAllCardsFromBoard(@PathVariable UUID uuid) {
        return retroBoardService.findAllCardsFromRetroBoard(uuid);
    }

    @PutMapping("/{uuid}/cards")
    public Mono<Card> addCardToRetroBoard(@PathVariable UUID uuid, @RequestBody Card card) {
        return retroBoardService.addCardToRetroBoard(uuid, card);
    }

    @GetMapping("/cards/{uuidCard}")
    public Mono<Card> getCardByUUID(@PathVariable UUID uuidCard) {
        return retroBoardService.findCardByUUID(uuidCard);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/cards/{uuidCard}")
    public Mono<Void> deleteCardFromRetroBoard(@PathVariable UUID uuid, @PathVariable UUID uuidCard) {
        return retroBoardService.removeCardByUUID(uuid, uuidCard);
    }
}
