package com.czy.learning.infranstructure.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    String code;
    String message;

    public BusinessException(ExceptionMessage em, String message) {
        super(message == null ? em.getMessage() : message);
        this.code = em.getCode();
        this.message = super.getMessage();
    }

    public BusinessException(ExceptionMessage em) {
        this(em, null);
    }

}
