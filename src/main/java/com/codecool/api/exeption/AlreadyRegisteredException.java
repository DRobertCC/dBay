package com.codecool.api.exeption;

public class AlreadyRegisteredException extends DbayException {

    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
