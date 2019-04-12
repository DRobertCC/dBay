package com.codecool.api;

import com.codecool.api.exeption.DbayException;
import com.codecool.api.exeption.NotAvailableInYourCountryException;

public class DbayCountry extends Dbay {

    private String country;

    public DbayCountry(String databasePath, String country) {
        super(databasePath);
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public void registerNewUser(User user) throws DbayException {
        if (user.getCountry().toLowerCase().equals(this.country.toLowerCase())) {
            super.registerNewUser(user);
        } else {
            throw new NotAvailableInYourCountryException("This service is not available in your country");
        }
    }
}
