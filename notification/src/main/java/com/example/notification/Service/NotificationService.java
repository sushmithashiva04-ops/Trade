package com.example.notification.Service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    // Logger configured to write to a file (via application.properties)
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void notifyCustomer(Map<String, Object> allocationEvent) {
        // Here, you would normally send email/SMS/push notifications
        // For now, we just log everything to a file
        logger.info("Notification sent to customer: {}", allocationEvent);
    }
}
