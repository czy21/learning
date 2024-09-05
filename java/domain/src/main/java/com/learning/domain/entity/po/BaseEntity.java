package com.learning.domain.entity.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    private String id;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createUser;
    private String updateUser;
}
