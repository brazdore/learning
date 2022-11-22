package com.example.spring.web.tema01.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class CalculatorExceptionHandler {

    @ExceptionHandler(CalculatorInputException.class)
    public ResponseEntity<StandardError> resourceNotFound(CalculatorInputException e, HttpServletRequest request) {
        String error = "[INVALID INPUT]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CalculatorDivisionException.class)
    public ResponseEntity<StandardError> resourceNotFound(CalculatorDivisionException e, HttpServletRequest request) {
        String error = "[IMPOSSIBLE TO DIVIDE BY ZERO]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
