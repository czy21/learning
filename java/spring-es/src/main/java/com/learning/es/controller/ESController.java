package com.learning.es.controller;

import com.learning.domain.entity.constant.QueueConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@RestController
@RequestMapping(path = "es")
public class ESController {

    @Autowired
    RestHighLevelClient esClient;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(path = "add")
    public Map<String, Object> add(@RequestBody Map<String, Object> param) throws Exception {
        IndexRequest request = new IndexRequest(QueueConstant.GLOBAL_QUEUE_TOPIC1);
        param.put("time", LocalDateTime.now(ZoneOffset.UTC).toString());
        String jsonString = objectMapper.writeValueAsString(param);
        request.source(jsonString, XContentType.JSON);
        esClient.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        return Map.of();
    }

}
