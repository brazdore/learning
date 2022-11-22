package com.example.tema05.exceptions.handlers;

import com.example.tema05.exceptions.InvalidAxleException;
import com.example.tema05.exceptions.InvalidPaymentException;
import com.example.tema05.exceptions.InvalidVehicleException;
import com.example.tema05.exceptions.TollPaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class TollExceptionHandler {

    @ExceptionHandler(TollPaymentException.class)
    public ResponseEntity<StandardError> notEnoughFunds(TollPaymentException e, HttpServletRequest request) {
        String error = "[INSUFFICIENT FUNDS]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidVehicleException.class)
    public ResponseEntity<StandardError> invalidVehicleInput(InvalidVehicleException e, HttpServletRequest request) {
        String error = "[INVALID VEHICLE TYPE]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<StandardError> invalidPaymentInput(InvalidPaymentException e, HttpServletRequest request) {
        String error = "[INVALID PAYMENT INPUT]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidAxleException.class)
    public ResponseEntity<StandardError> invalidAxleInput(InvalidAxleException e, HttpServletRequest request) {
        String error = "[INVALID AXLE INPUT]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
