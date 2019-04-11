package com.codecool.api;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String country;

    public User(String userName, String password, String fullName, String email, String country) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.country = country;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    /*    @Override
            public boolean equals(Object obj) {     // Az .equals() alapesetben igy néz ki:
                return this == obj;   // Ez csak akkor igaz, ha mindkettő ugyanaz a példány / azonos memóriacímen.
            }
        */
// Alt+Ins - equals() and hashCode():
    @Override          // Override-olom az .equals()-t, hogy érték szerint hasonlítson, és ne referencia szerint.
    public boolean equals(Object o) {
        if (this == o) return true; /// Két obj akkor egyenlő, ha ugyanabból az osztályból származnak és ...
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName); // ... ugyanaz a Usernevük
    }

    @Override
    public int hashCode() {

        return Objects.hash(userName);
    }
// A == referencia szerint hasonlít össze objektumokat (memóriacímek alapján) és az .equals() is. Összetett objektumoknál a programozónak meg kell adnis, hogyan hasonlítson össze a program, mely változók alapján tekintsen két példányt azonosnak.
// https://www.sitepoint.com/implement-javas-equals-method-correctly/


    @Override
    public String toString() {
        return String.format("%-15s", userName) +
                " │ " + String.format("%-30s", fullName) +
                " │ " + String.format("%-30s", email) +
                " │ " + String.format("%-20s", country);
    }
}

