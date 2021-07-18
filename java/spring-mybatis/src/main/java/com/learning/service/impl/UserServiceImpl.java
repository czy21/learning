package com.learning.service.impl;

import com.learning.entity.dto.UserDTO;
import com.learning.entity.po.UserPO;
import com.learning.mapper.UserMapper;
import com.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

//    @Transactional
    @Override
    public void update(UserDTO dto) {
        String id = UUID.randomUUID().toString();
        UserPO addU = new UserPO();
        addU.setId(id);
        addU.setAccount(dto.getAccount());
        addU.setUserName(dto.getUserName());
        addU.setDepartmentId(dto.getDepartmentId());
        userMapper.insert(addU);
        UserPO updateU = new UserPO();
        updateU.setId(id);
        updateU.setEmail("update2 Email");
        userMapper.update(updateU);
    }
}
