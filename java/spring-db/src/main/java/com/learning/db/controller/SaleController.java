package com.learning.db.controller;

import com.learning.domain.entity.po.SalePO;
import com.learning.db.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "sale")
public class SaleController {
    @Autowired
    SaleService saleService;


    @PostMapping(path = "batchTest")
    public Map<String, Object> batchTest() {
        saleService.batchTest();
        return Map.of("status", "success");
    }

    @PostMapping(path = "search")
    public List<SalePO> search(){
        return saleService.findPage();
    }

}
