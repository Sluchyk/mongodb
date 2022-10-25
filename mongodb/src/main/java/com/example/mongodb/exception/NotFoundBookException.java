package com.example.mongodb.exception;

public class NotFoundBookException extends Exception {
    public NotFoundBookException(String message) {
        super(message);
    }
}
