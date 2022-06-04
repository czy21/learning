package com.czy.learning.pulsar.core;


import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;

public class PulsarTemplate {

    private PulsarFactory factory;

    public PulsarTemplate(PulsarFactory factory) {
        this.factory = factory;
    }


    public PulsarFactory getFactory() {
        return factory;
    }

    @SuppressWarnings("unchecked")
    public <T> MessageId send(String topic, T message) throws PulsarClientException {
        return ((Producer<T>) factory.getProducerMap().get(topic)).send(message);
    }


}
