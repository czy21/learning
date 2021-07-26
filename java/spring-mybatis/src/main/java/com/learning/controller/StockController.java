package com.learning.controller;

import com.clearning.entity.dto.StockDTO;
import com.learning.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "stock")
public class StockController {


    @Autowired
    StockService stockService;

    @PostMapping(path = "add")
    public Map<String, Object> update(@RequestBody StockDTO input) {
        stockService.add(input);
        return Map.of("status", "success");
    }

    @PostMapping(path = "sale")
    public Map<String, Object> detail(@RequestBody StockDTO input) {
        stockService.reduce(input);
        return Map.of("status", "success");
    }

}
