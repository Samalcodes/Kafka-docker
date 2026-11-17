package com.training.notificationservice.service;

import com.training.notificationservice.model.EmployeeEvent;
import com.training.notificationservice.util.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void processEmployeeNotification(EmployeeEvent event) {
        if (event == null) {
            log.error("Received null employee event. Skipping processing.");
            return;
        }

        String email = event.getEmail();
        if (EmailValidator.isValid(email)) {
            log.info("Notification sent to: {}", email);
        } else {
            log.error("Invalid email format received for employee '{}': '{}'. Skipping processing.",
                    event.getName(), email);
        }
    }
}
