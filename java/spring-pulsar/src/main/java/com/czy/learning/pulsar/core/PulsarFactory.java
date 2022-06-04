package com.czy.learning.pulsar.core;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

import java.util.Map;

public class PulsarFactory {
    private PulsarClient pulsarClient;
    private Map<String, Producer<?>> producerMap;
    private Map<String, Consumer<?>> consumerMap;

    public PulsarClient getPulsarClient() {
        return pulsarClient;
    }

    public void setPulsarClient(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }

    public Map<String, Producer<?>> getProducerMap() {
        return producerMap;
    }

    public void setProducerMap(Map<String, Producer<?>> producerMap) {
        this.producerMap = producerMap;
    }

    public Map<String, Consumer<?>> getConsumerMap() {
        return consumerMap;
    }

    public void setConsumerMap(Map<String, Consumer<?>> consumerMap) {
        this.consumerMap = consumerMap;
    }
}
