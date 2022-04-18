package com.learning.es.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.domain.entity.constant.QueueConstant;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@RestController
@RequestMapping(path = "order")
public class ESController {

    @Autowired
    RestHighLevelClient esClient;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(path = "add")
    public Map<String, Object> add(@RequestBody Map<String, Object> param) throws Exception {
        IndexRequest request = new IndexRequest(String.join("-", QueueConstant.GLOBAL_QUEUE_PREFIX, "log1"));
        param.put("time", LocalDateTime.now(ZoneOffset.UTC).toString());
        String jsonString = objectMapper.writeValueAsString(param);
        request.source(jsonString, XContentType.JSON);
        esClient.index(request, RequestOptions.DEFAULT);
        return Map.of();
    }
}
