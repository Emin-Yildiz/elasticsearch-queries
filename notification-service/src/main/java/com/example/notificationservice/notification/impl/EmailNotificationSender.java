package com.example.notificationservice.notification.impl;

import com.example.notificationservice.notification.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotificationSender implements NotificationSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationSender.class);

    @Override
    public boolean sender() {
        logger.info("Email notification send");
        return true;
    }

}
