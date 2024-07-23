package com.apress.users.events;


import com.apress.users.web.socket.UserSocket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Slf4j
@AllArgsConstructor
@Component
public class UserLogs {

    private UserSocket userSocket;

    @Async
    @EventListener
    void userActiveStatusEventHandler(UserActivatedEvent event){
        log.info("User {} active status: {}",event.getEmail(),event.isActive());
        userSocket.userLogSocket(new HashMap<>(){{
            put("action",event.getName());
            put("email",event.getEmail());
            put("isActive",event.isActive());
        }});
    }

    @Async
    @EventListener
    void userDeletedEventHandler(UserRemovedEvent event){
        log.info("User {} DELETED at {}",event.getEmail(),event.getRemoved());
        userSocket.userLogSocket(new HashMap<>(){{
            put("action",event.getName());
            put("email",event.getEmail());
            put("datetime",event.getRemoved().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }});
    }

}
