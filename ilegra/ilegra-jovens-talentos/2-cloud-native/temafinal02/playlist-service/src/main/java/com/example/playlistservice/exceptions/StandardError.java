package com.example.playlistservice.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String msg;
    private String path;

    public StandardError() {
    }

    public StandardError(Instant timestamp, Integer status, String error, String msg, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.msg = msg;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }

    public String getPath() {
        return path;
    }
}
