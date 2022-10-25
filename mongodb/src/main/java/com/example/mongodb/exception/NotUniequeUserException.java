package com.example.mongodb.exception;

public class NotUniequeUserException extends Exception {
    public NotUniequeUserException(String message) {
        super(message);
    }
}
