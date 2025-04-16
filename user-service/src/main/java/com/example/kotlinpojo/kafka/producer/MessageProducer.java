package com.example.kotlinpojo.kafka.producer;

import com.example.global.enums.MDCConstants;
import com.example.global.kafka.NotificationSendEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceNotificationSendEvent(String topicName, NotificationSendEvent notificationSendEvent) {
        String requestId = MDC.get(MDCConstants.REQUEST_ID.key());
        String transactionId = MDC.get(MDCConstants.TRANSACTION_ID.key());
        logger.info("[{}][Starting produceNotificationSendEvent] Topic Name: {}", requestId,topicName);
        ProducerRecord<String, Object> record = new ProducerRecord<>(topicName, notificationSendEvent);
        record.headers().add(MDCConstants.REQUEST_ID.key(), requestId.getBytes(StandardCharsets.UTF_8));
        record.headers().add(MDCConstants.TRANSACTION_ID.key(), transactionId.getBytes(StandardCharsets.UTF_8));
        kafkaTemplate.send(record);
        logger.info("[{}][Completed produceNotificationSendEvent] Topic Name: {}", requestId,topicName);
    }
}
