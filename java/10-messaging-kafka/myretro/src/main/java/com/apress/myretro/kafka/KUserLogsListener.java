package com.apress.myretro.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KUserLogsListener {

    private static final String TOPIC = "user-logs";
    private static final String GROUP_ID = "my-retro";

    @KafkaListener(topics = {TOPIC}, groupId = GROUP_ID)
    public void userLogs(UserEvent userEvent) {
        log.info("User logs: {}", userEvent);
    }
}
