package com.learning.cache.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.domain.entity.constant.QueueConstant;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;

import java.util.Map;


public class RedisStreamListener1 implements StreamListener<String, MapRecord<String, String, String>> {

    StringRedisTemplate redisTemplate;
    ObjectMapper objectMapper;

    public RedisStreamListener1(StringRedisTemplate redisTemplate,
                                ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map<String, String> msgMap = message.getValue();
        msgMap.put("a", "hello");
        System.out.println(msgMap);
        redisTemplate.opsForStream().acknowledge(QueueConstant.GLOBAL_QUEUE_GROUP1, message);
        redisTemplate.opsForStream().delete(message);
    }
}
