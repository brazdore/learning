package com.example.sum.exceptions;

public class InvalidUsernameInputException extends RuntimeException {
    public InvalidUsernameInputException(String message) {
        super(message);
    }
}
