package com.algaworks.CommentService.api.config.web;

import com.algaworks.CommentService.api.client.ModerationClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ModerationClientException.class)
    public ProblemDetail handleModerationClientException(ModerationClientException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        problemDetail.setTitle("Palavra não permitida!!!");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/api/moderate"));
        return problemDetail;
    }
}
