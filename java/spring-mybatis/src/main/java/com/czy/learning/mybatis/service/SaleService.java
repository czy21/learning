package com.czy.learning.mybatis.service;

import com.czy.learning.domain.entity.po.SalePO;

import java.util.List;

public interface SaleService {
    void batchTest();
    List<SalePO> findPage();
}
