package com.training.employeeservice.kafka;

import com.training.employeeservice.exception.KafkaPublishException;
import com.training.employeeservice.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class EmployeeKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(EmployeeKafkaProducer.class);

    private final KafkaTemplate<String, Employee> kafkaTemplate;

    @Value("${app.kafka.topic.employee-created}")
    private String topicName;

    public EmployeeKafkaProducer(KafkaTemplate<String, Employee> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmployeeCreatedEvent(Employee employee) {
        try {
            log.info("Publishing employee-created event to topic '{}': {}", topicName, employee);
            kafkaTemplate.send(topicName, employee.getId(), employee).get(10, TimeUnit.SECONDS);
            log.info("Successfully published employee-created event for ID: {}", employee.getId());
        } catch (Exception ex) {
            log.error("Failed to publish employee event to Kafka for ID: {}", employee.getId(), ex);
            throw new KafkaPublishException("Failed to publish employee event to Kafka: " + ex.getMessage(), ex);
        }
    }
}
