package com.apress.myretro.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class UserEventConfig {

    @Bean
    MessageConverter messageConverter() {
        return new MessageConverter() {
            @Override
            public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object fromMessage(Message message) throws JMSException, MessageConversionException {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                try {
                    String _type = message.getStringProperty("_type");
                    UserEvent event =  mapper.readValue(message.getBody(String.class), UserEvent.class);
                    event.setAction("Activated");
                    if (_type.contains("Removed"))
                        event.setAction("Removed");

                    return event;
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            }
        };
    }
}
