package com.learning.cache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.cache.listener.RedisPubSubListener1;
import com.learning.cache.listener.RedisStreamListener1;
import com.learning.domain.entity.constant.QueueConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
public class RedisConfigure {

    @Bean
    public StreamListener<String, MapRecord<String, String, String>> redisStreamListener1(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        return new RedisStreamListener1(redisTemplate, objectMapper);
    }

    @Bean
    public Subscription subscription1(RedisConnectionFactory redisConnectionFactory,
                                      @Qualifier("redisStreamListener1") StreamListener<String, MapRecord<String, String, String>> redisListener1) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .batchSize(100)
                .pollTimeout(Duration.ZERO)
                .build();
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer
                .create(redisConnectionFactory, containerOptions);
        StreamMessageListenerContainer.ConsumerStreamReadRequest<String> listenerRequest = StreamMessageListenerContainer.StreamReadRequest
                .builder(StreamOffset.create(QueueConstant.GLOBAL_QUEUE_TOPIC1, ReadOffset.lastConsumed()))
                .cancelOnError((ex) -> false)
                .consumer(Consumer.from(QueueConstant.GLOBAL_QUEUE_GROUP1, "consumer-1"))
                .errorHandler(t -> {
                })
                .build();
        Subscription subscription = container.register(listenerRequest, redisListener1);
        container.start();
        return subscription;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory,
                                                        ObjectMapper objectMapper,
                                                        StringRedisTemplate redisTemplate) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(new MessageListenerAdapter(new RedisPubSubListener1(objectMapper, redisTemplate)), new ChannelTopic(String.join("-", QueueConstant.GLOBAL_QUEUE_PREFIX, "pus-sub", "1")));
        return container;
    }

}
