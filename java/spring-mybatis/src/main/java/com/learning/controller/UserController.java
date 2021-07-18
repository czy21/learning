package com.learning.controller;

import com.clearning.entity.dto.UserDTO;
import com.learning.mapper.UserMapper;
import com.learning.service.UserService;
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
        return userMapper.selectAll();
    }

    @PostMapping(path = "update")
    public Map<String, Object> update(@RequestBody UserDTO input) {
        userService.update(input);
        return Map.of("status", "success");
    }
}
