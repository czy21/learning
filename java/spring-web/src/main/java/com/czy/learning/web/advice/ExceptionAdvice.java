package com.czy.learning.web.advice;


import com.czy.learning.infranstructure.exception.BusinessException;
import com.czy.learning.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = BaseController.class)
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    public static final String UN_KNOW_SERVER_ERROR = "UN_KNOW_SERVER_ERROR";

    public static final String METHOD_ARGUMENT_ERROR = "METHOD_ARGUMENT_ERROR";

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionHandler(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put(BaseController.RESPONSE_TIMESTAMP_KEY, LocalDateTime.now());
        Map<String, Object> error = new HashMap<>();
        error.put("code", e instanceof BusinessException ? ((BusinessException) e).getCode() : UN_KNOW_SERVER_ERROR);
        error.put("message", e.getMessage());
        result.put(BaseController.RESPONSE_ERROR_KEY, error);
        logger.error("", e);
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        String errorStr = objectErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", METHOD_ARGUMENT_ERROR);
        ret.put("message", errorStr);
        return ret;

    }
}
