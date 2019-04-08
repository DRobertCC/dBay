package com.codecool.api.exeption;

public class NoSuchUserNamePasswordCombinationException extends DbayException {

    public NoSuchUserNamePasswordCombinationException(String s) {
        super(s);
    }
}
