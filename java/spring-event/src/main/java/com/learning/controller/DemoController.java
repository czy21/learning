package com.learning.controller;

import com.learning.event.DemoPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private DemoPublisher demoPublisher;

    @GetMapping(path = "demo")
    public String demo() {
        demoPublisher.publish("nihao");
        return "finishe";
    }
}
