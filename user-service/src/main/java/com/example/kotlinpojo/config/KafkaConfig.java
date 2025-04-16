package com.example.kotlinpojo.config;

import jakarta.validation.constraints.NotBlank;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "com.example.kotlinpojo.config")
public class KafkaConfig {

    @NotBlank
    private String kafkaNotificationSendTopicPartitions;

    public static final String NOTIFICATION_SEND_TOPIC = "notification_send_topic";

    @Bean
    public NewTopic createNotificationSendTopic() {
        return TopicBuilder.name(NOTIFICATION_SEND_TOPIC)
                .partitions(Integer.parseInt(kafkaNotificationSendTopicPartitions)).build();
    }

    @Bean
    public KafkaTemplate<String, Object> messageKafkaTemplate(ProducerFactory<String, Object> messageProducerFactory) {
        KafkaTemplate<String, Object> template = new KafkaTemplate<>(messageProducerFactory);
        template.setObservationEnabled(true);
        return template;
    }

    public @NotBlank String getKafkaNotificationSendTopicPartitions() {
        return kafkaNotificationSendTopicPartitions;
    }

    public void setKafkaNotificationSendTopicPartitions(@NotBlank String kafkaNotificationSendTopicPartitions) {
        this.kafkaNotificationSendTopicPartitions = kafkaNotificationSendTopicPartitions;
    }
}
