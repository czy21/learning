package com.learning.controller;

import com.clearning.entity.dto.UserDTO;
import com.learning.mapper.UserMapper;
import com.learning.service.SaleService;
import com.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
