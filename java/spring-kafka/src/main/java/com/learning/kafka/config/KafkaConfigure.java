package com.learning.kafka.config;

import com.learning.domain.entity.constant.QueueConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfigure {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(QueueConstant.GLOBAL_QUEUE_TOPIC1)
                .partitions(5)
                .replicas(1)
                .build();
    }

//    @KafkaListener(topics = {"learning-topic1"})
//    public void listen(String message) {
//        System.out.println(message);
//    }
}
