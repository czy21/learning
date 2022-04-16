package com.learning.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class QueueConfig {

    public static String LEARN_TOPIC_1 = "learn-topic1";

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(QueueConfig.LEARN_TOPIC_1)
                .partitions(5)
                .replicas(1)
                .build();
    }

    @KafkaListener(topics = {"learn-topic1"})
    public void listen(Map<String, Object> message) {
        System.out.println(message);
    }
}
