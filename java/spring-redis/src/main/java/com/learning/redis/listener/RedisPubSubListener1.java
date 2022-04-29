package com.learning.redis.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RedisPubSubListener1 implements MessageListener {

    ObjectMapper objectMapper;
    StringRedisTemplate redisTemplate;

    public RedisPubSubListener1(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Map<String, Object> msgObj;
        try {
            msgObj = objectMapper.readValue(message.getBody(), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            return;
        }
        String msgId = (String) msgObj.get("msgId");
        boolean flag = Optional.ofNullable(redisTemplate.opsForValue().setIfAbsent(String.join(":", "unique", msgId), "0", 30, TimeUnit.SECONDS)).orElse(false);
        if (flag) {
            redisTemplate.opsForValue().increment("counter");
            System.out.println(msgObj);
        }

    }
}
