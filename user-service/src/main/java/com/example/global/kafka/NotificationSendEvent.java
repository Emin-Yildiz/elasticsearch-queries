package com.example.global.kafka;

import java.time.LocalDateTime;

public record NotificationSendEvent(
        String recipient,
        String subject,
        String message,
        NotificationType type, // EMAIL, SMS, PUSH
        LocalDateTime createdAt
) {
}
