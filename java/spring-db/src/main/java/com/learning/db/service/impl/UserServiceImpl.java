package com.learning.db.service.impl;

import com.learning.domain.entity.dto.UserDTO;
import com.learning.domain.entity.po.UserPO;
import com.learning.db.mapper.UserMapper;
import com.learning.db.service.UserService;
import com.learning.web.annotation.Option;
import com.learning.web.annotation.OptionProvider;
import com.learning.web.model.SimpleItemModel;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@OptionProvider
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

    @Option("option1")
    public List<SimpleItemModel<String>> option1() {
        return List.of(SimpleItemModel.of("a", "bbb"));
    }

    @Option("option2")
    public List<SimpleItemModel<String>> option2() {
        return List.of(SimpleItemModel.of("a", "bbb"));
    }

}
