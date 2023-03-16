package com.emontazysta.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<String>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        Map<String, List<String>> errorsMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            List<String> errors = errorsMap.get(error.getObjectName());
            if (errors == null) {
                errors = new ArrayList<>();
            }
            errors.add(error.getDefaultMessage());
            errorsMap.put(error.getObjectName(), errors);
        });
        return errorsMap;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
//        exception.printStackTrace();
//        Map<String, String> errorsMap = new HashMap<>();
//        exception.getBindingResult().getFieldErrors().forEach(error -> {
//            errorsMap.put(error.getField(), error.getDefaultMessage());
//        });
//        return errorsMap;
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void handleEmptyResultDataAccessException(EmptyResultDataAccessException emptyResultDataAccessException) {

        log.debug(emptyResultDataAccessException.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {

        log.debug(entityNotFoundException.getMessage());
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public String illegalArgumentException(IllegalArgumentException illegalArgumentException) {
//
//       return  illegalArgumentException.getMessage();
//
//    }
}
