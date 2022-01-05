package com.clearning.entity.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    private String id;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private String createdUser;

    private String modifiedUser;
}
