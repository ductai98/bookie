package com.taild.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventConsumer {

    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        log.info("Received message='{}'", message);
    }
}
