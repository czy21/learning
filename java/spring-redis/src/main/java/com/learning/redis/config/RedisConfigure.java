package com.learning.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.domain.entity.constant.QueueConstant;
import com.learning.redis.listener.RedisListener1;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
public class RedisConfigure {

    @Bean
    public StreamListener<String, MapRecord<String, String, String>> redisListener1(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        return new RedisListener1(redisTemplate, objectMapper);
    }

    @Bean
    public Subscription subscription1(RedisConnectionFactory redisConnectionFactory,
                                      @Qualifier("redisListener1") StreamListener<String, MapRecord<String, String, String>> redisListener1) {
        var containerOptions = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .batchSize(100)
                .pollTimeout(Duration.ZERO)
                .build();
        var container = StreamMessageListenerContainer
                .create(redisConnectionFactory, containerOptions);
        StreamMessageListenerContainer.ConsumerStreamReadRequest<String> listenerRequest = StreamMessageListenerContainer.StreamReadRequest
                .builder(StreamOffset.create(QueueConstant.GLOBAL_QUEUE_TOPIC1, ReadOffset.lastConsumed()))
                .cancelOnError((ex) -> false)
                .consumer(Consumer.from(QueueConstant.GLOBAL_QUEUE_GROUP1, "consumer-1"))
                .errorHandler(t -> {
                })
                .build();
        var subscription = container.register(listenerRequest, redisListener1);
        container.start();
        return subscription;
    }

}
