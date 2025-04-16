package com.example.notificationservice.kafka.consumer;

import com.example.global.enums.MDCConstants;
import com.example.global.kafka.NotificationSendEvent;
import com.example.notificationservice.notification.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class NotificationSendConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationSendConsumer.class);

    private final NotificationService notificationService;

    public NotificationSendConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification_send_topic",  groupId = "group-id-1", containerFactory = "messageContainerFactory")
    public void handleNotificationSenderEvent(ConsumerRecord<String, String> record,  Acknowledgment acknowledgment, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition){
        String requestId = new String(record.headers().lastHeader(MDCConstants.REQUEST_ID.key()).value(), StandardCharsets.UTF_8);
        String parentTransactionId = new String(record.headers().lastHeader(MDCConstants.TRANSACTION_ID.key()).value(), StandardCharsets.UTF_8);
        String transactionId = UUID.randomUUID().toString();
        MDC.put(MDCConstants.REQUEST_ID.key(), requestId);
        MDC.put(MDCConstants.PARENT_TRANSACTION_ID.key(), parentTransactionId);
        MDC.put(MDCConstants.TRANSACTION_ID.key(), transactionId);
        try {
            logger.info("[{}][Starting handleNotificationSenderEvent] Kafka Listener Started. Topic Name: 'notification_send_topic', Time [{}]", requestId, new Date());
            //notificationService.notificationSender(record.value());
            acknowledgment.acknowledge();
        } finally {
            MDC.clear();
            logger.info("[{}][Completed handleNotificationSenderEvent] Kafka Listener Completed. Topic Name: 'notification_send_topic', Time [{}]",requestId, new Date());
        }
    }

}
