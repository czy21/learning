package com.learning.pulsar.controller;

import com.czy.pulsar.core.PulsarTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.domain.entity.constant.QueueConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "pulsar")
public class PulsarController {

    @Autowired
    PulsarTemplate pulsarTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(path = "put")
    public Map<String, Object> put(@RequestBody Map<String, Object> param) throws Exception {
        pulsarTemplate.send(QueueConstant.GLOBAL_QUEUE_TOPIC1, param);
        return Map.of();
    }

}
