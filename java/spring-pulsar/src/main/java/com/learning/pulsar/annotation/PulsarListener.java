package com.learning.pulsar.annotation;

import org.apache.pulsar.client.api.SubscriptionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PulsarListener {
    String[] topics() default {};

    Class<?> clazz() default byte[].class;

    Serialization serialization() default Serialization.JSON;

    SubscriptionType subscriptionType() default SubscriptionType.Exclusive;

    String subscriptionName();

    boolean batch() default false;

    enum Serialization {
        JSON,
        AVRO,
        STRING,
        BYTE,
        PROTOBUF
    }
}
