package com.learning.service;

import com.clearning.entity.dto.StockDTO;

public interface StockService {
    void add(StockDTO dto);

    void reduce(StockDTO dto);
}
