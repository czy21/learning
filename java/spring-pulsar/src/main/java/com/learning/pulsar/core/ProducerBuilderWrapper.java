package com.learning.pulsar.core;

import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;

public interface ProducerBuilderWrapper {
    ProducerBuilder<?> apply(PulsarClient client);
}
