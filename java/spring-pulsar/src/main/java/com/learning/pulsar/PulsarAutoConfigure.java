package com.learning.pulsar;

import com.learning.pulsar.core.ProducerBuilderWrapper;
import com.learning.pulsar.core.PulsarListenerScanner;
import com.learning.pulsar.core.PulsarTemplate;
import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({PulsarProperties.class})
public class PulsarAutoConfigure {

    PulsarProperties pulsarProperties;
    List<ProducerBuilderWrapper> producerBuilderWrappers;

    public PulsarAutoConfigure(PulsarProperties pulsarProperties,
                               ObjectProvider<List<ProducerBuilderWrapper>> producerWrappers) {
        this.pulsarProperties = pulsarProperties;
        this.producerBuilderWrappers = Optional.ofNullable(producerWrappers.getIfAvailable()).orElse(new ArrayList<>());
    }

    @Bean
    @ConditionalOnMissingBean(name = "pulsarClient")
    public PulsarClient pulsarClient() throws PulsarClientException {
        ClientBuilder pulsarClientBuilder = PulsarClient.builder()
                .serviceUrl(pulsarProperties.getServiceUrl())
                .ioThreads(pulsarProperties.getIoThreads())
                .listenerThreads(pulsarProperties.getListenerThreads())
                .enableTcpNoDelay(pulsarProperties.isEnableTcpNoDelay())
                .keepAliveInterval(pulsarProperties.getKeepAliveIntervalSec(), TimeUnit.SECONDS)
                .connectionTimeout(pulsarProperties.getConnectionTimeoutSec(), TimeUnit.SECONDS)
                .operationTimeout(pulsarProperties.getOperationTimeoutSec(), TimeUnit.SECONDS)
                .startingBackoffInterval(pulsarProperties.getStartingBackoffIntervalMs(), TimeUnit.MILLISECONDS)
                .maxBackoffInterval(pulsarProperties.getMaxBackoffIntervalSec(), TimeUnit.SECONDS);
        return pulsarClientBuilder.build();
    }

    @Bean
    public PulsarListenerScanner pulsarListenerScanner(PulsarClient pulsarClient) {
        return new PulsarListenerScanner(pulsarClient);
    }

    @Bean
    public PulsarTemplate pulsarTemplate(PulsarClient client) {
        Map<String, Producer<?>> producerMap = getProducerMap(client);
        return new PulsarTemplate(producerMap);
    }

    private Map<String, Producer<?>> getProducerMap(PulsarClient client) {
        Map<String, Producer<?>> ps = new HashMap<>();
        try {
            for (ProducerBuilderWrapper t : producerBuilderWrappers) {
                Producer<?> p = t.apply(client).create();
                ps.put(p.getTopic(), p);
            }
            return ps;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
