package com.learning.cache.controller;


import com.learning.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "cache")
public class CacheController {

    @Autowired
    CacheService cacheService;

    @PostMapping(path = "test1")
    public Map<String, Object> test1(@RequestBody Map<String, Object> param) {
        return Map.of("ret", cacheService.search());
    }
}
