package com.learning.domain.entity.model;

import lombok.Data;

@Data
public class PageModel {
    private Integer pageIndex = 1;
    private Integer pageSize = 10;
    private Integer total;
}
