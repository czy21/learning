package com.learning.mapper;


import com.clearning.entity.po.StockPO;
import com.clearning.entity.po.UserPO;

import java.util.List;
import java.util.Map;

public interface StockMapper {

    StockPO selectOne(StockPO query);

    void insert(StockPO userPO);

    void reduce(StockPO userPO);
}
