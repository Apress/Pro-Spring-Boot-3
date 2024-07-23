package com.apress.myretro.service;

import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.persistence.RetroBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Service
public class RetroBoardAndCardService {

    private RetroBoardRepository retroBoardRepository;

    public Flux<RetroBoard> findAllRetroBoards(){
        return this.retroBoardRepository.findAll();
    }

    public Mono<RetroBoard> findRetroBoardById(UUID uuid){
        return this.retroBoardRepository.findById(uuid);
    }

    public Mono<RetroBoard> saveRetroBoard(RetroBoard retroBoard){
        return this.retroBoardRepository.save(retroBoard);
    }

    public Mono<Void> deleteRetroBoardById(UUID uuid){
        this.retroBoardRepository.deleteById(uuid);
        return Mono.empty();
    }

}
