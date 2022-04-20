package com.learning.controller;

import com.learning.event.DemoPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "event")
public class EventController {

    @Autowired
    private DemoPublisher demoPublisher;

    @GetMapping(path = "demo")
    public String demo() {
        demoPublisher.publish("nihao");
        return "finishe";
    }

    @GetMapping(path = "test1")
    public Map<String,Object> test1() {
        return Map.of();
    }

}
