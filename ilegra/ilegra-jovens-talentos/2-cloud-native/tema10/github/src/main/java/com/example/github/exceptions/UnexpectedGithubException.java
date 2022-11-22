package com.example.github.exceptions;

public class UnexpectedGithubException extends RuntimeException {
    public UnexpectedGithubException(String message) {
        super(message);
    }
}
