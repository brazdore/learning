package com.example.songservice.exceptions;

public class InvalidSongIdListFormatException extends RuntimeException {
    public InvalidSongIdListFormatException(String invalidIdList) {
        super("Invalid ID-LIST. [ID-LIST] must be a comma-separated number list. (" + invalidIdList + ") is not valid.");
    }
}
