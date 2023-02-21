package com.czy.learning.web.advice;


import com.czy.learning.infranstructure.exception.BusinessException;
import com.czy.learning.web.controller.BaseController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(assignableTypes = BaseController.class)
public class ExceptionAdvice {

    public static final String UN_KNOW_SERVER_ERROR = "UN_KNOW_SERVER_ERROR";

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionHandler(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put(BaseController.RESPONSE_TIMESTAMP_KEY, LocalDateTime.now());
        Map<String, Object> error = new HashMap<>();
        error.put("code", e instanceof BusinessException ? ((BusinessException) e).getCode() : UN_KNOW_SERVER_ERROR);
        error.put("message", e.getMessage());
        result.put(BaseController.RESPONSE_ERROR_KEY, error);
        e.printStackTrace();
        return result;
    }

}
