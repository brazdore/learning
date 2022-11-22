package com.example.playlistservice.exceptions;

public class InvalidPlaylistIdFormatException extends RuntimeException {
    public InvalidPlaylistIdFormatException(String invalidId) {
        super("Invalid ID. [ID] must be a valid number. (" + invalidId + ") is not valid.");
    }
}
