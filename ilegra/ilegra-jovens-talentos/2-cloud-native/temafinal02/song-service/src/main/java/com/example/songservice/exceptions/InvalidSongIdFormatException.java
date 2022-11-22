package com.example.songservice.exceptions;

public class InvalidSongIdFormatException extends RuntimeException {
    public InvalidSongIdFormatException(String invalidId) {
        super("Invalid ID. [ID] must be a valid number. (" + invalidId + ") is not valid.");
    }
}
