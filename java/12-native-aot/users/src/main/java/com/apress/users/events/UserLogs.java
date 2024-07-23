package com.apress.users.events;


import com.apress.users.actuator.LogEventEndpoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class UserLogs {

    private LogEventEndpoint logEventEndpoint;

    @Async
    @EventListener
    void userActiveStatusEventHandler(UserActivatedEvent event){
        if (logEventEndpoint.isEnable())
            log.info("{} User {} active status: {} {}",logEventEndpoint.config().getPrefix(),
                    event.getEmail(),event.isActive(),logEventEndpoint.config().getPostfix());
        else
            log.info("User {} active status: {}",event.getEmail(),event.isActive());
    }

    @Async
    @EventListener
    void userDeletedEventHandler(UserRemovedEvent event){
        if (logEventEndpoint.isEnable())
            log.info("{} User {} DELETED at {} {}",logEventEndpoint.config().getPrefix(),
                    event.getEmail(),event.getRemoved(),logEventEndpoint.config().getPostfix());
        else
            log.info("{} User {} DELETED at {} {}",event.getEmail(),event.getRemoved());
    }

}
