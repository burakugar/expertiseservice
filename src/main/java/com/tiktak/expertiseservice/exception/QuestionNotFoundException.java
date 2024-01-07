package com.tiktak.expertiseservice.exception;

public final class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(final String message) {
        super(message);
    }

}
