package com.sanyao.springaiaction.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/04 22:18
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions( MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail =  ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");

        List<String> validationMessages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();

        problemDetail.setProperty("validationErrors", validationMessages);
        return problemDetail;
    }

}
