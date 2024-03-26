package com.learning.domain.entity.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String account;
    private String password;
    private String userName;
    private String email;
    private String departmentId;
}
