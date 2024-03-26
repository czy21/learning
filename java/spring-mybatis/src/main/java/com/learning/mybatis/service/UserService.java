package com.learning.mybatis.service;

import com.learning.domain.entity.dto.UserDTO;

public interface UserService {
    void update(UserDTO dto);

    void batchUpdate();
}
