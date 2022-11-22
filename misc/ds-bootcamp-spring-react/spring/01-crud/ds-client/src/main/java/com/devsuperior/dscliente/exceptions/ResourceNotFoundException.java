package com.devsuperior.dscliente.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    private static final Long serialVersionUID = 1L;
    private static final String msg = "No element was found for ID: ";

    public ResourceNotFoundException(Long id) {
        super(msg + id);
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
