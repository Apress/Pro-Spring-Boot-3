package com.apress.users.jms;

import com.apress.users.events.UserActivatedEvent;
import jakarta.jms.JMSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListeners {

    @JmsListener(destination = UserJmsConfig.DESTINATION_ACTIVATED)
    public void onActivatedUserEvent(UserActivatedEvent event){
        log.info("JMS User {}",event);
    }

    // Generic ActiveMQMessage > jakarta.jms.Message

    @JmsListener(destination = UserJmsConfig.DESTINATION_REMOVED)
    public void onRemovedUserEvent(Object event) throws JMSException {
        log.info("JMS User DELETED message: {} ", event);
    }

    // Is necessary add the ObjectMapper to convert the message to the UserRemovedEvent
    /*
    @JmsListener(destination = UserJmsConfig.DESTINATION_REMOVED)
    public void onRemovedUserEvent(UserRemovedEvent event) throws JMSException {
        log.info("JMS User DELETED message: {} ", event);
    }
    */
}

