package com.apress.myretro.web;

import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.rsocket.User;
import com.apress.myretro.rsocket.UserClient;
import com.apress.myretro.service.RetroBoardAndCardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/retros")
public class RetroBoardController {

    private RetroBoardAndCardService retroBoardAndCardService;
    private UserClient userClient;

    @GetMapping
    public Flux<RetroBoard> getAllRetroBoards(){
        return this.retroBoardAndCardService.findAllRetroBoards();
    }

    @GetMapping("/{uuid}")
    public Mono<ResponseEntity<RetroBoard>> findRetroBoardById(@PathVariable UUID uuid){
        Mono<RetroBoard> retroBoardMono = this.retroBoardAndCardService.findRetroBoardById(uuid);
        return retroBoardMono.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = { RequestMethod.POST,RequestMethod.PUT})
    public Mono<ResponseEntity<RetroBoard>> saveRetroBoard(@Valid @NotNull @RequestBody RetroBoard retroBoard, UriComponentsBuilder componentsBuilder){
        Mono<RetroBoard> retroBoardMono = this.retroBoardAndCardService.saveRetroBoard(retroBoard);
        URI uri = componentsBuilder.path("/{uuid}").buildAndExpand(retroBoardMono.doOnNext(result -> result.getRetroBoardId()).subscribe()).toUri();
        return retroBoardMono.map( retroBoard1 -> ResponseEntity.created(uri).build());
    }

    @DeleteMapping("/{uuid}")
    public Mono<ResponseEntity<Void>> deleteRetroBoardById(@PathVariable UUID uuid){
        this.retroBoardAndCardService.deleteRetroBoardById(uuid);
        return Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
    }
}
