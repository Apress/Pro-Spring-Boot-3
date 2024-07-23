package com.apress.users.events;


import com.apress.users.kafka.KUserLogs;
import com.apress.users.kafka.KConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Slf4j
@Component
public class UserLogs {

    private KUserLogs kUserLogs;
    @Async
    @EventListener
    void userActiveStatusEventHandler(UserActivatedEvent event){
        log.info("User {} active status: {}",event.getEmail(),event.isActive());
        kUserLogs.send(KConfig.TOPIC,event.getEmail(),new UserEvent("STATUS",event.getEmail(),event.isActive(), LocalDateTime.now()));
    }

    @Async
    @EventListener
    void userDeletedEventHandler(UserRemovedEvent event){
        log.info("User {} DELETED at {}",event.getEmail(),event.getRemoved());
        kUserLogs.send(KConfig.TOPIC,event.getEmail(),new UserEvent("DELETED",event.getEmail(),false, LocalDateTime.now()));
    }

}
