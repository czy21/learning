package com.learning.domain.entity.dto;

import lombok.Data;

@Data
public class MessageDTO<T> {
    private String name;
    private String group;
    private T payload;
}
