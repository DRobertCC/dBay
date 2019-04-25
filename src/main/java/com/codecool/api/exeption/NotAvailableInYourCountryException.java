package com.codecool.api.exeption;

public class NotAvailableInYourCountryException extends DbayException {

    public NotAvailableInYourCountryException(String s) {
        super(s);
    }
}
