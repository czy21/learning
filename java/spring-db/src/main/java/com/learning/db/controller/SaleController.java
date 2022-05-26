package com.learning.db.controller;

import com.github.pagehelper.PageHelper;
import com.learning.db.mapper.UserMapper;
import com.learning.domain.entity.po.SalePO;
import com.learning.db.service.SaleService;
import com.learning.domain.util.PageIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "sale")
public class SaleController {
    @Autowired
    SaleService saleService;
    @Autowired
    UserMapper userMapper;


    @PostMapping(path = "batchTest")
    public Map<String, Object> batchTest() {
        saleService.batchTest();
        return Map.of("status", "success");
    }

    @PostMapping(path = "search")
    public List<SalePO> search() {
        return saleService.findPage();
    }

    @GetMapping(path = "testPageNext")
    public Map<String, Object> testPageNext(@RequestParam String pageSize) {
        new PageIterator<>(1, Integer.parseInt(pageSize), (s, e) -> {
            PageHelper.startPage(s, e);
            return userMapper.selectAll();
        }).forEachRemaining(t -> {
            System.out.println(t.get("user_name"));
        });
        return Map.of();
    }


}
