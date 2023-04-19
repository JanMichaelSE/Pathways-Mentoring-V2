package com.pathways.app.exception;

public class UserPasswordDoesNotMatchException extends RuntimeException {
    public UserPasswordDoesNotMatchException(String message) {
        super(message);
    }
}
