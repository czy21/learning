package com.learning.redis.controller;


import com.learning.domain.entity.constant.QueueConstant;
import com.learning.domain.entity.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "redis")
public class RedisController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping(path = "stream/push1")
    public Map<String, Object> redisQueuePush(@RequestBody MessageDTO<Map<String, Object>> param) {
        redisTemplate.opsForStream().add(StreamRecords.newRecord().in(QueueConstant.GLOBAL_QUEUE_TOPIC1).ofMap(param.getPayload()));
        return Map.of();
    }

    @GetMapping(path = "stream/info")
    public StreamInfo.XInfoStream redisStreamCount() {
        return redisTemplate.opsForStream().info(QueueConstant.GLOBAL_QUEUE_TOPIC1);
    }

    @GetMapping(path = "stream/recreate")
    public Map<String, Object> redisStreamPrune() {
        redisTemplate.delete(QueueConstant.GLOBAL_QUEUE_TOPIC1);
        redisTemplate.opsForStream().createGroup(QueueConstant.GLOBAL_QUEUE_TOPIC1, ReadOffset.from("0-0"), QueueConstant.GLOBAL_QUEUE_GROUP1);
        return Map.of();
    }

}
