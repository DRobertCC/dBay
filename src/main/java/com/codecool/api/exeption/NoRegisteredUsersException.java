package com.codecool.api.exeption;

public class NoRegisteredUsersException extends DbayException {

    public NoRegisteredUsersException(String s) {
        super(s);
    }
}
