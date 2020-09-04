package com.softserve.itacademy.exception;

public class NullEntityReferenceException extends RuntimeException {
    public NullEntityReferenceException() {    }

    public NullEntityReferenceException(String message) {
        super(message);
    }
}
