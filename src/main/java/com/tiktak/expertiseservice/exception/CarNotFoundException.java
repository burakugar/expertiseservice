package com.tiktak.expertiseservice.exception;

public final class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(final String message) {
        super(message);
    }
}

