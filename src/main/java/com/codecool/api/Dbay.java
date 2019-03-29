package com.codecool.api;

import com.codecool.api.exeption.DbayException;
import com.codecool.api.exeption.NotLoggedInException;

public class Dbay {

    private static int nextId = 0;

    private User activeUser = null; // will change after login

    public void changeActiveUser(User user) {
        activeUser = user;
    }




    public void listNewItem() throws DbayException {
        checkActiveUser();
    }

    private void checkActiveUser() throws NotLoggedInException {
        if (activeUser != null) {
            throw new NotLoggedInException("You must be logged in to post new listings");
        }
    }


}
