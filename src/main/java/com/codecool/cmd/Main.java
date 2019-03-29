package com.codecool.cmd;

import com.codecool.api.*;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static Dbay dbay = new Dbay(); // Lehet statikus, hiszen csak egy lesz belőe per dbay. Így nem kell argumentumként átadni sem minden metódusnak.

    public static void main(String[] args) throws InterruptedException {
        XMLLoader xmlLoader = new XMLLoader();

        List<User> users = new ArrayList<>(xmlLoader.getUsers("data/Users.xml"));
        System.out.println(users.get(0));

        List<Car> cars = new ArrayList<>(xmlLoader.getCars("data/Cars.xml"));
        System.out.println(cars.get(0));
    }

}
