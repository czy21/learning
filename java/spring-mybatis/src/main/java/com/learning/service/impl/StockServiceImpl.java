package com.learning.service.impl;

import com.clearning.entity.dto.StockDTO;
import com.clearning.entity.po.StockPO;
import com.learning.mapper.StockMapper;
import com.learning.service.StockService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockMapper stockMapper;
    @Autowired
    RedissonClient redissonClient;

    @Override
    public void add(StockDTO dto) {
        StockPO po = new StockPO();
        po.setName(dto.getName());
        if (stockMapper.selectOne(po) != null) {
            throw new RuntimeException(StringUtils.join(List.of(dto.getName(), "已存在"), " "));
        }
        po.setId(UUID.randomUUID().toString());
        po.setCount(dto.getCount());
        stockMapper.insert(po);
    }

    @Override
    public void reduce(StockDTO dto) {
        StockPO po = new StockPO();
        po.setId(dto.getId());
        String lockKey = StringUtils.join(List.of("lock", dto.getId()), ":");
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            StockPO stock = stockMapper.selectOne(po);
            if (stock == null) {
                throw new RuntimeException(StringUtils.join(List.of("该商品不存在"), " "));
            }
            if (stock.getCount() == 0) {
                throw new RuntimeException(StringUtils.join(List.of(stock.getName(), "库存不足"), " "));
            }
            stockMapper.reduce(po);
        } finally {
            lock.unlock();
        }

    }
}

