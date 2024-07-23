package com.apress.myretro.service;

import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.events.RetroBoardEvent;
import com.apress.myretro.events.RetroBoardEventAction;
import com.apress.myretro.exceptions.RetroBoardNotFoundException;
import com.apress.myretro.persistence.RetroBoardRepository;
import com.apress.users.UserEvent;
import com.apress.users.model.User;
import io.micrometer.core.instrument.Counter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class RetroBoardAndCardService {

    private RetroBoardRepository retroBoardRepository;

    private ApplicationEventPublisher eventPublisher;

    private Counter retroBoardCounter;

    private ApplicationEventPublisher events;

    //private UserClient userClient;

    // Uncomment this to see the effect of the @Observed annotation (Custom Observation))
    //@Observed(name = "retro-boards",contextualName = "getAllRetroBoards")
    public Iterable<RetroBoard> getAllRetroBoards(){
        return this.retroBoardRepository.findAll();
    }

    // Uncomment this to see the effect of the @Observed annotation (Custom Observation))
    //@Observed(name = "retro-board-id",contextualName = "findRetroBoardById")
    public RetroBoard findRetroBoardById(UUID uuid){
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

    // Error
    /*
    @Async
    @EventListener
    public void newSavedUser(User user){
        log.info("New user saved: {} {}",user.getEmail(), LocalDateTime.now());
    }
    */

    // Uncomment this to see the effect of the @TransactionalEventListener annotation - This is Spring Modulith way of doing it
    @Async
    @TransactionalEventListener
    public void newSavedUser(UserEvent userEvent){
        log.info("New user saved: {} {} {}",userEvent.getEmail(), userEvent.getAction(), LocalDateTime.now());
    }

}
