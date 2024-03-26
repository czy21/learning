package com.learning.mybatis.controller;

import lombok.Data;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "gc")
public class GController {
    /*
     java heap space
     */
    @PostMapping(path = "test1")
    public List<Map<String, Object>> test1() throws InterruptedException {
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1 = Map.of("name", "李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李李");
        Map<String, Object> map2 = Map.of("name", "世");
        Map<String, Object> map3 = Map.of("name", "民");
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        System.out.println(GraphLayout.parseInstance(maps).toPrintable());
        return maps;
    }

    @Data
    public class Order {
        private String orderNo;
        private String status;
    }

    public static void main(String[] args) {

        List<Order> orders1 = new ArrayList<>();
        List<Order> orders2 = new ArrayList<>();
        orders1.forEach(t -> {
            if (orders2.stream().anyMatch(s -> s.getOrderNo().equals(t))) {
                t.setStatus("y");
            }
        });


    }
}
