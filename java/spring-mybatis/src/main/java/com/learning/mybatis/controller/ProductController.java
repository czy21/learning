package com.learning.mybatis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "product")
@Slf4j
public class ProductController {

//    @PostMapping(path = "search")
//    public Mono<Map<String, Object>> search() throws InterruptedException {
//        log.info("start flux");
//        var ret = Mono.fromSupplier(this::doSomeThing);
//        log.info("end flux");
//        return ret;
//    }

    @PostMapping(path = "search1")
    public Map<String, Object> search1() throws InterruptedException {
        log.info("start flux");
        var ret = doSomeThing();
        log.info("end flux");
        return ret;
    }

    private Map<String, Object> doSomeThing() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Map.of("name", "product1");
    }
}
