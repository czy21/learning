package com.learning.cache.service.impl;

import com.learning.cache.service.CacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CacheServiceImpl implements CacheService {

    @Cacheable(cacheNames = "system", cacheManager = "caffeineCacheManager")
    @Override
    public Map<String, Object> search() {
        return Map.of();
    }
}
