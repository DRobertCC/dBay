package com.codecool.api;

import java.util.List;

public class DbayNormal extends Dbay {

    public DbayNormal(String databasePath) {
        super(databasePath);
    }

    public DbayNormal(List<User> users, List<Item> items, int id) {
        super(users, items, id);
    }

}
