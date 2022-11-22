package com.devsuperior.dscatalog.exceptions;

public class DatabaseException extends RuntimeException {
    private static final Long serialVersionUID = 1L;
    private static final String msg = "Error accessing database when performing: ";

    public DatabaseException(String operation) {
        super(msg + operation.toUpperCase());
    }
}
