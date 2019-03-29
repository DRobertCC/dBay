package com.codecool.api.exeption;

public class NotLoggedInException extends DbayException {

    public NotLoggedInException(String s) {
        super(s);
    }
}
