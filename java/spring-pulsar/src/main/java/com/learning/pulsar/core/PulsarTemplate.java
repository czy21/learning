package com.learning.pulsar.core;


import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Map;

public class PulsarTemplate {

    private Map<String, Producer<?>> producerMap;

    public PulsarTemplate(Map<String, Producer<?>> producerMap) {
        this.producerMap = producerMap;
    }

    @SuppressWarnings("unchecked")
    public <T> MessageId send(String topic, T message) throws PulsarClientException {
        return ((Producer<T>) producerMap.get(topic)).send(message);
    }


}
