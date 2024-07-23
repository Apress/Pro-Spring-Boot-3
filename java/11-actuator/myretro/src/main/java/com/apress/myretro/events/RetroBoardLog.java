package com.apress.myretro.events;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetroBoardLog {

    @Async
    @EventListener
    public void retroBoardEventHandler(RetroBoardEvent event){
        log.info("EVENT:::RetroBoard[{}] Action[{}] happen at: {}",event.getRetroBoardId(),event.getAction(),event.getHappenAt());
    }
}
