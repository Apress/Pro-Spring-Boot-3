package com.apress.users.kafka;

import com.apress.users.events.UserEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class KUserLogs {

    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void send(String topicName, String key, UserEvent value) {

        var future = kafkaTemplate.send(topicName, key, value);

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            log.info("User sent to Kafka topic : {}", value);
        });
    }

}
