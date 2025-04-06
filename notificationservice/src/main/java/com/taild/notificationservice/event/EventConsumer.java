package com.taild.notificationservice.event;

import com.taild.commonservice.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventConsumer {


    private final EmailService emailService;

    public EventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RetryableTopic(
            attempts = "4", // 3 topics retry + 1 topic DLQ
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetriableException.class, RuntimeException.class}
    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        log.info("Received message='{}'", message);
    }


    @DltHandler
    public void handleDLT(@Payload String message) {
        log.error("Dead lettered message='{}'", message);
    }

    @KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
    public void listenEmail(String message) {
        log.info("Received email='{}'", message);
        emailService.sendEmail(message, "Test Email", "This is a test email", false);

    }
}
