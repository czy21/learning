package com.learning.service.impl;

import com.clearning.entity.dto.StockDTO;
import com.clearning.entity.po.StockPO;
import com.learning.mapper.StockMapper;
import com.learning.service.StockService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockMapper stockMapper;

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
        if (stockMapper.selectOne(po) == null) {
            throw new RuntimeException(StringUtils.join(List.of(dto.getName(), "不存在"), " "));
        }
        stockMapper.reduce(po);
    }
}

