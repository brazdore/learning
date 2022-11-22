package com.example.github.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class GithubExceptionHandler {

    @ExceptionHandler(GithubUserNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(GithubUserNotFoundException e, HttpServletRequest request) {
        String error = "[GITHUB USER NOT FOUND]";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnexpectedGithubException.class)
    public ResponseEntity<StandardError> unexpected(UnexpectedGithubException e, HttpServletRequest request) {
        String error = "[UNEXPECTED GITHUB ERROR]";
        HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
