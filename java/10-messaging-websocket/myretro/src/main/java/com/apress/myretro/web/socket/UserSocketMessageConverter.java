package com.apress.myretro.web.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class UserSocketMessageConverter implements MessageConverter {

    public Object fromMessage(Message<?> message, Class<?> targetClass) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Event userEvent = null;
        try {
            String m = new String((byte[]) message.getPayload());
            userEvent = mapper.readValue(m, Event.class);

        }catch(Exception ex){
            log.error("Cannot Deserialize - {}",ex.getMessage());
        }
        return userEvent;
    }

    @Override
    public Message<?> toMessage(Object payload, MessageHeaders headers) {
        throw new UnsupportedOperationException();
    }
}
