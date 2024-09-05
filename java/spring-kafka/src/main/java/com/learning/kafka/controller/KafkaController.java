package com.learning.kafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.domain.entity.constant.QueueConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "kafka")
public class KafkaController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(path = "put")
    public Map<String, Object> put(@RequestBody Map<String, Object> param) throws JsonProcessingException {
        kafkaTemplate.send(QueueConstant.GLOBAL_QUEUE_TOPIC1, param);
        return Map.of();
    }

}
