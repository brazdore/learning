package com.example.tema08.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Instant timestamp;
    private final Integer status;
    private final String error;
    private final String path;

    public StandardError(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    @Override
    public String toString() {
        return "{\n    \"timestamp\": \"" + timestamp + "\"" +
                ",\n    \"status\": \"" + status + "\"" +
                ",\n    \"error\": \"" + error + "\"" +
                ",\n    \"path\": \"" + path + "\"" +
                "\n}";
    }
}
