package com.learning.kafka.controller;

import com.learning.kafka.config.QueueConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "queue")
public class QueueController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping(path = "put")
    public Map<String, Object> put(@RequestBody Map<String, Object> param) {
        kafkaTemplate.send(QueueConfig.LEARN_TOPIC_1, param);
        return Map.of();
    }

}
