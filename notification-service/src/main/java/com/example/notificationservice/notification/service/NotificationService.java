package com.example.notificationservice.notification.service;

import com.example.global.kafka.NotificationSendEvent;
import com.example.global.kafka.NotificationType;
import com.example.notificationservice.notification.NotificationSender;
import com.example.notificationservice.notification.impl.EmailNotificationSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notificationSender(NotificationSendEvent event) {
        NotificationType type = event.getType();
        NotificationSender sender = null;
        switch (type) {
            case MAIL -> sender = new EmailNotificationSender();
        }
        boolean isSuccess = sender.sender();
    }
}
