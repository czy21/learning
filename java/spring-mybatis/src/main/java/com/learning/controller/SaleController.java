package com.learning.controller;

import com.learning.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
