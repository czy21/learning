package com.learning.db.service.impl;

import com.learning.domain.entity.dto.UserDTO;
import com.learning.domain.entity.po.UserPO;
import com.learning.db.mapper.UserMapper;
import com.learning.db.service.UserService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

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

    @Override
    public void batchUpdate() {

    }

}
