package com.learning.domain.entity.po;

import lombok.Data;

@Data
public class UserPO {
    private String id;
    private String account;
    private String password;
    private String userName;
    private String email;
    private String departmentId;
}
