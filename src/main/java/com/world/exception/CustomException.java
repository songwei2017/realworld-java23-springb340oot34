package com.world.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}