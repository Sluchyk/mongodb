package com.example.mongodb.exception;

public class NotUniqueBookException extends Exception {
    public NotUniqueBookException(String message) {
        super(message);
    }
}
