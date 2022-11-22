package com.example.songservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class SongExceptionHandler {

    @ExceptionHandler(InvalidSongIdFormatException.class)
    public ResponseEntity<StandardError> invalidSongIdFormat(InvalidSongIdFormatException e, HttpServletRequest request) {
        String error = "[INVALID ID]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidSongIdListFormatException.class)
    public ResponseEntity<StandardError> invalidSongIdListFormat(InvalidSongIdListFormatException e, HttpServletRequest request) {
        String error = "[INVALID ID-LIST]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
