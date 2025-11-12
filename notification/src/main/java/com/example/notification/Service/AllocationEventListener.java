package com.example.notification.Service;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AllocationEventListener {

    private final NotificationService notificationService;

    public AllocationEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @JmsListener(
    	    destination = "TRADE_ALLOCATION_TOPIC",
    	    containerFactory = "jmsListenerContainerFactory",
    	    subscription = "notification-subscription"
    	)

    public void receiveAllocationEvent(Map<String, Object> allocationEvent) {
        // Log to file
        notificationService.notifyCustomer(allocationEvent);

        // Print to console
        System.out.println("📢 Notification Event Received: " + allocationEvent);
    }
}
