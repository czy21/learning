package com.learning.mapper;

import com.learning.entity.po.UserPO;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<Map<String, Object>> selectAll();

    void insert(UserPO userPO);

    void update(UserPO userPO);
}
