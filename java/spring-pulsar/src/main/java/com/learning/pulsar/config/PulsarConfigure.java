package com.learning.pulsar.config;

import com.learning.domain.entity.constant.QueueConstant;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PulsarConfigure {

    PulsarClient pulsarClient;

    public PulsarConfigure(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }

    @Bean
    public Producer<Map> pulsarTopic1() throws Exception {
        return pulsarClient.newProducer(Schema.JSON(Map.class)).topic(QueueConstant.GLOBAL_QUEUE_TOPIC1).create();
    }
}
