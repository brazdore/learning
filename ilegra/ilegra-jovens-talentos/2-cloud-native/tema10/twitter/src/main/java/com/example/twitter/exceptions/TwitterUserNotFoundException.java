package com.example.twitter.exceptions;

public class TwitterUserNotFoundException extends RuntimeException {
    public TwitterUserNotFoundException(String message) {
        super(message);
    }
}
