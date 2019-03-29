package com.codecool.api.exeption;

public class DbayException extends Exception { // nem Throwable

    public DbayException(String message) {
        super(message);
    }
}
