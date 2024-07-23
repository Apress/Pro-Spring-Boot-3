package com.apress.myretro.service;

import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.client.UserClient;
import com.apress.myretro.events.RetroBoardEvent;
import com.apress.myretro.events.RetroBoardEventAction;
import com.apress.myretro.exceptions.RetroBoardNotFoundException;
import com.apress.myretro.persistence.RetroBoardRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
@Observed(name = "retro-board-service",contextualName = "retroBoardAndCardService")
public class RetroBoardAndCardService {

    private RetroBoardRepository retroBoardRepository;

    private ApplicationEventPublisher eventPublisher;

    private Counter retroBoardCounter;

    private UserClient userClient;

    // Uncomment this to see the effect of the @Observed annotation (Custom Observation))
    //@Observed(name = "retro-boards",contextualName = "getAllRetroBoards")
    public Iterable<RetroBoard> getAllRetroBoards(){
        log.info("Getting all retro boards");
        return this.retroBoardRepository.findAll();
    }

    // Uncomment this to see the effect of the @Observed annotation (Custom Observation))
    //@Observed(name = "retro-board-id",contextualName = "findRetroBoardById")
    public RetroBoard findRetroBoardById(UUID uuid){
        log.info("Getting retro board by id: {}", uuid);
        return this.retroBoardRepository.findById(uuid).orElseThrow(RetroBoardNotFoundException::new);
    }

    public RetroBoard saveOrUpdateRetroBoard(RetroBoard retroBoard){
        RetroBoard retroBoardResult = this.retroBoardRepository.save(retroBoard);
        eventPublisher.publishEvent(new RetroBoardEvent(retroBoardResult.getRetroBoardId(), RetroBoardEventAction.CHANGED, LocalDateTime.now()));

        this.retroBoardCounter.increment();

        return retroBoardResult;
    }

    public void deleteRetroBoardById(UUID uuid){
        this.retroBoardRepository.deleteById(uuid);
        eventPublisher.publishEvent(new RetroBoardEvent(uuid,RetroBoardEventAction.DELETED,LocalDateTime.now()));
    }

    //@Observed(name = "users",contextualName = "getAllUsers")
    public void getAllUsers(){
        log.info("Getting all users");
        userClient.getAllUsers().subscribe(
                user -> log.info("User: {}", user),
                error -> log.error("Error: {}", error),
                () -> log.info("Completed")
        );
    }
}
