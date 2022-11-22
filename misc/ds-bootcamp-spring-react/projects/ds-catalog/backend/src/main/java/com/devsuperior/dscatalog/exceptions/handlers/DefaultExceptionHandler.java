package com.devsuperior.dscatalog.exceptions.handlers;

import com.devsuperior.dscatalog.dtos.StandardError;
import com.devsuperior.dscatalog.exceptions.DatabaseException;
import com.devsuperior.dscatalog.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(
                new StandardError(
                        Instant.now(),
                        NOT_FOUND.value(),
                        NOT_FOUND.getReasonPhrase(),
                        e.getMessage(),
                        request.getRequestURI())
        );
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new StandardError(
                        Instant.now(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        e.getMessage(),
                        request.getRequestURI())
        );
    }
}
