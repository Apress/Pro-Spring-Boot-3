package com.apress.users.web.socket;


import lombok.AllArgsConstructor;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@Component
public class UserSocket {

    private MessageSendingOperations<String> messageSendingOperations;

    public void userLogSocket(Map<String,Object> event){
        Map<String, Object> map = new HashMap<>(){{
            put("event",event);
            put("version","1.0");
            put("time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }};
        this.messageSendingOperations.convertAndSend("/topic/user-logs",map);
    }
}
