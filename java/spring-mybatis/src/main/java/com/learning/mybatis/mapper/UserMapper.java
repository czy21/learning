package com.learning.mybatis.mapper;


import com.learning.domain.entity.po.UserPO;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<Map<String, Object>> selectAll(Integer skipRows,Integer pageSize);

    void insert(UserPO userPO);

    void update(UserPO userPO);
}
