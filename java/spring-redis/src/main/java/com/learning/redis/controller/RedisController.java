package com.learning.redis.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "api1")
public class RedisController {

    @PostMapping(path = "test1")
    public Map<String, Object> test1(@RequestBody Map<String, Object> param) {

        return Map.of();
    }
}
