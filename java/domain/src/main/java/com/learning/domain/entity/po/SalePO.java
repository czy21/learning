package com.learning.domain.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class SalePO extends BaseEntity {
    private LocalDateTime saleDate;
    private String fromInstitutionCode;
    private String fromInstitutionName;
    private String fromInstitutionCodeFormat;
    private String toInstitutionCode;
    private String toInstitutionName;
    private String toInstitutionNameFormat;
    private String productCode;
    private String productName;
    private String productSpec;
    private String productCodeFormat;
    private String productBatch;
    private BigDecimal productPrice;
    private BigDecimal productQuantity;
    private BigDecimal productAmount;
    private String producer;
    private String productUnit;
    private String productUnitFormat;
    private String productQuantityFormat;

}
