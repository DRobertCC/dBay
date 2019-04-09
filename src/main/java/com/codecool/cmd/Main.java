package com.codecool.cmd;


public class Main {
    //private static Dbay dbay = new Dbay(); // Lehet statikus, hiszen csak egy lesz belőe per dbay. Így nem kell argumentumként átadni sem minden metódusnak.

    public static void main(String[] args) throws InterruptedException {
        Menu menu = new Menu();

        while (true) {
            menu.handleMenu();
            Thread.sleep(100);
            menu.choose();
        }


//        try {
//            dbay.listNewCar();
//        } catch (DbayException e) {
//            System.err.println(e.getMessage());
//        }

/*
        try {
            dbay.listNewMotorCycle();
        } catch (DbayException e) {
            System.err.println(e.getMessage());
        }
*/

//        dbay.printNextId(); // Ok
//        dbay.printUsers(); // Ok
//        dbay.printCars(); // Ok
//        dbay.printMotorCycles(); // Ok
//
//        menu.register();
//        dbay.printUsers();
//
//        try {
//            dbay.logIn();
//        } catch (DbayException e) {
//            System.err.println(e.getMessage());
//        }
//
//        dbay.updateNextId(); // Car     4 │ Toyota Corolla                 │ COUPE         │ 1998 │  2.5 litres │   2   │  true │ €2590.0
//        dbay.logOut(); // Ok
    }

}
