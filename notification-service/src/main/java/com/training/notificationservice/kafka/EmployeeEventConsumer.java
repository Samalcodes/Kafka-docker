package com.training.notificationservice.kafka;

import com.training.notificationservice.model.EmployeeEvent;
import com.training.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmployeeEventConsumer.class);

    private final NotificationService notificationService;

    public EmployeeEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.employee-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeEmployeeCreatedEvent(EmployeeEvent event) {
        try {
            log.info("Received message from topic 'employee-created': {}", event);
            notificationService.processEmployeeNotification(event);
        } catch (Exception ex) {
            log.error("Unexpected error while processing employee event: {}", event, ex);
        }
    }
}
