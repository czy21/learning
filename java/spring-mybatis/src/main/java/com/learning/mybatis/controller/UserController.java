package com.learning.mybatis.controller;

import com.learning.domain.entity.dto.UserDTO;
import com.learning.mybatis.mapper.UserMapper;
import com.learning.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @GetMapping(path = "list")
    public List<Map<String, Object>> demo() {
        return userMapper.selectAll(1,10);
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

    @GetMapping(path = "batchUpdate")
    public Map<String, Object> batchUpdate() {
        userService.batchUpdate();
        return Map.of("status", "success");
    }

}
