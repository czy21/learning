package com.learning.mybatis.service;

import com.learning.domain.entity.po.SalePO;

import java.util.List;

public interface SaleService {
    void batchTest();
    List<SalePO> findPage();
}
