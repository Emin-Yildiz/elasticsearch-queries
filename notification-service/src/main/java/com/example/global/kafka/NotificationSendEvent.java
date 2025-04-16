package com.example.global.kafka;

import java.time.LocalDateTime;

public class NotificationSendEvent {
    private String recipient;
    private String subject;
    private String message;
    private NotificationType type;
    private LocalDateTime createdAt;

    public NotificationSendEvent() {
    }

    public NotificationSendEvent(String recipient, String subject, String message, NotificationType type, LocalDateTime createdAt) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
