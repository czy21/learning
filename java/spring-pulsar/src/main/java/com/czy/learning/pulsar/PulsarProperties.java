package com.czy.learning.pulsar;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "pulsar")
public class PulsarProperties {
    private String serviceUrl = "pulsar://localhost:6650";
    private Integer ioThreads = 10;
    private Integer listenerThreads = 10;
    private Integer keepAliveIntervalSec = 20;
    private Integer connectionTimeoutSec = 10;
    private Integer operationTimeoutSec = 15;
    private Integer startingBackoffIntervalMs = 100;
    private Integer maxBackoffIntervalSec = 10;
    private String consumerNameDelimiter = "";
    private String namespace = "default";
    private String tenant = "public";
    private boolean enableTcpNoDelay;

}
