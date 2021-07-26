package com.learning.controller;

import com.clearning.entity.dto.UserDTO;
import com.learning.mapper.UserMapper;
import com.learning.service.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @Autowired
    RedissonClient redissonClient;

    @GetMapping(path = "list")
    public List<Map<String, Object>> demo() {
        return userMapper.selectAll();
    }

    @PostMapping(path = "update")
    public Map<String, Object> update(@RequestBody UserDTO input) {
        userService.update(input);
        return Map.of("status", "success");
    }


    @PostMapping(path = "detail")
    public Map<String, Object> detail() {

        return Map.of("status", "success");
    }

    @PostMapping(path = "lock1")
    public Map<String, Object> lock1() throws InterruptedException {
        RLock lock = redissonClient.getLock("lock1");
        lock.lock();
        TimeUnit.SECONDS.sleep(10);
        lock.unlock();
        return Map.of("status", "success");
    }

    @PostMapping(path = "lock2")
    public Map<String, Object> lock2() throws InterruptedException {
        RLock lock = redissonClient.getLock("lock1");
        lock.lock();
        TimeUnit.SECONDS.sleep(10);
        lock.unlock();
        return Map.of("status", "success");
    }

}
