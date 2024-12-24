package com.world.exception.handler;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.world.exception.CustomException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleIllegalRuntimeException(RuntimeException e) {
        StaticLog.error(e.getMessage(), "ERROR");
        return new ResponseEntity<>("RuntimeException", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIException(Exception e) {
        return new ResponseEntity<>("Exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleIThrowable(Throwable e) {
        return new ResponseEntity<>("Throwable", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());
        Error err = new Error(new Error.Errors(messages ) );

        // 创建一个ObjectMapper实例
        ObjectMapper objectMapper = new ObjectMapper();
        // 将record对象转换为JSON字符串
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(err);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(jsonString, HttpStatus.BAD_REQUEST);
    }

    public record Error(Errors errors) {
        public record Errors(List<String> body){
        }
    }


}