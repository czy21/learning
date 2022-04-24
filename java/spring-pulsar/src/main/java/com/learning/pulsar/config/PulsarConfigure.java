package com.learning.pulsar.config;

import com.czy.pulsar.core.ProducerWrapper;
import com.learning.domain.entity.constant.QueueConstant;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PulsarConfigure {

    @Bean
    ProducerWrapper pulsarTopic1() {
        return client -> {
            try {
                return client.newProducer(Schema.JSON(Map.class)).topic(QueueConstant.GLOBAL_QUEUE_TOPIC1).create();
            } catch (PulsarClientException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
