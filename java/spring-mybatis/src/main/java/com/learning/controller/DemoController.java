package com.learning.controller;

import com.learning.mapper.Demo1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "demo1")
public class DemoController {

    @Autowired
    Demo1Mapper demo1Mapper;

    @GetMapping(path = "list")
    public List<Map<String, Object>> demo() {
        return demo1Mapper.selectAll();
    }
}
