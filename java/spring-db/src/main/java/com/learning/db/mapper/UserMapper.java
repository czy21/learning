package com.learning.db.mapper;


import com.learning.domain.entity.po.UserPO;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<Map<String, Object>> selectAll();

    void insert(UserPO userPO);

    void update(UserPO userPO);
}