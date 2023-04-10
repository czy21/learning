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

    private Map<String, Object> createErrorResponse(ErrorModel errorObj) {
        Map<String, Object> result = new HashMap<>();
        result.put(BaseController.RESPONSE_TIMESTAMP_KEY, LocalDateTime.now());
        result.put(BaseController.RESPONSE_ERROR_KEY, errorObj);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        ErrorModel em = new ErrorModel();
        em.setCode(e instanceof BusinessException ? ((BusinessException) e).getCode() : UN_KNOW_SERVER_ERROR);
        em.setMessage(e.getMessage());
        return createErrorResponse(em);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        String message = objectErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        ErrorModel em = new ErrorModel();
        em.setCode(METHOD_ARGUMENT_ERROR);
        em.setMessage(message);
        return createErrorResponse(em);
    }

    private static class ErrorModel {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
