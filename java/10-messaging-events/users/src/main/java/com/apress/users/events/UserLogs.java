package com.apress.users.events;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserLogs {
    @Async
    @EventListener
    void userActiveStatusEventHandler(UserActivatedEvent event){
        log.info("User {} active status: {}",event.getEmail(),event.isActive());
    }

    @Async
    @EventListener
    void userDeletedEventHandler(UserRemovedEvent event){
        log.info("User {} DELETED at {}",event.getEmail(),event.getRemoved());
    }

}
