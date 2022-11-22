package com.example.spring.web.tema01.exceptions;

public class CalculatorInputException extends RuntimeException {
    public CalculatorInputException(String message) {
        super(message);
    }
}
