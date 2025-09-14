package com.sanyao.springaiaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 23:01
 */
@RestControllerAdvice
public class ModerationExceptionHandler {

    @ExceptionHandler(ModerationException.class)
    public ProblemDetail moderationException(ModerationException ex) {
        var problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Moderation Exception");
        return problemDetail;
    }

}
