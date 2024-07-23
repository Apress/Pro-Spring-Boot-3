package com.apress.myretro.service;

import com.apress.myretro.board.RetroBoard;
import com.apress.myretro.events.RetroBoardEvent;
import com.apress.myretro.events.RetroBoardEventAction;
import com.apress.myretro.exceptions.RetroBoardNotFoundException;
import com.apress.myretro.persistence.RetroBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RetroBoardAndCardService {

    private RetroBoardRepository retroBoardRepository;

    private ApplicationEventPublisher eventPublisher;

    public Iterable<RetroBoard> getAllRetroBoards(){
        return this.retroBoardRepository.findAll();
    }

    public RetroBoard findRetroBoardById(UUID uuid){
        return this.retroBoardRepository.findById(uuid).orElseThrow(RetroBoardNotFoundException::new);
    }

    public RetroBoard saveOrUpdateRetroBoard(RetroBoard retroBoard){
        RetroBoard retroBoardResult = this.retroBoardRepository.save(retroBoard);
        eventPublisher.publishEvent(new RetroBoardEvent(retroBoardResult.getRetroBoardId(), RetroBoardEventAction.CHANGED, LocalDateTime.now()));
        return retroBoardResult;
    }

    public void deleteRetroBoardById(UUID uuid){
        this.retroBoardRepository.deleteById(uuid);
        eventPublisher.publishEvent(new RetroBoardEvent(uuid,RetroBoardEventAction.DELETED,LocalDateTime.now()));
    }

}
