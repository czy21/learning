package com.learning.pulsar.config;


import com.czy.pulsar.core.ProducerBuilderWrapper;
import com.learning.domain.entity.constant.QueueConstant;
import org.apache.pulsar.client.api.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PulsarConfigure {

    @Bean
    ProducerBuilderWrapper pulsarTopic1() {
        return client -> client.newProducer(Schema.JSON(Map.class)).topic(QueueConstant.GLOBAL_QUEUE_TOPIC1);
    }

    @Bean
    ProducerBuilderWrapper pulsarTopic2() {
        return client -> client.newProducer(Schema.JSON(Map.class)).topic("tenant1/default/topic1");
    }
}
