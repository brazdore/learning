package com.example.twitter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class TwitterExceptionHandler {

    @ExceptionHandler(TwitterUserNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(TwitterUserNotFoundException e, HttpServletRequest request) {
        String error = "[TWITTER USER NOT FOUND]";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<StandardError> invalidInput(InvalidInputException e, HttpServletRequest request) {
        String error = "[INVALID TWITTER USERNAME]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
