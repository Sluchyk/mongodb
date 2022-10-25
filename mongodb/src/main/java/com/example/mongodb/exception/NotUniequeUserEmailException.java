package com.example.mongodb.exception;

public class NotUniequeUserEmailException extends  Exception{
   public NotUniequeUserEmailException (String message)
    {
        super(message);
    }
}
