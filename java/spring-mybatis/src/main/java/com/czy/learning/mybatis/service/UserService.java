package com.czy.learning.mybatis.service;

import com.czy.learning.domain.entity.dto.UserDTO;

public interface UserService {
    void update(UserDTO dto);

    void batchUpdate();
}
