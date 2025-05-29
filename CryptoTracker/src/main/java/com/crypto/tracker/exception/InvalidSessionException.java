package com.crypto.tracker.exception;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException(String message) {
        super(message);
    }
}