package com.codecool.api;


import com.codecool.api.exeption.DbayException;
import com.codecool.api.exeption.NotLoggedInException;

public class Menu {

    private static Dbay dbay; // Lehet statikus, hiszen csak egy lesz belőe per dbay. Így nem kell argumentumként átadni sem minden metódusnak.

    public Menu() {
        dbay = new Dbay();
    }

    public void handleMenu() {

        String[] options = {
                "Register",
                "Log in",
                "Show registered users",
                "   Show cars",
                "   Show motorcycles",
                "   List new Car",
                "   List new Motorcycle",
                "Buy an item",
                "Log out"};

        printMenu("\n*** Welcome to dBay. The offline stuff marketplace. ***\n\n " + "           Main menu           Current user: " + dbay.getActiveUser(), options, "Exit program");
    }

    public void printMenu(String title, String[] listOptions, String exitMessage) {
        System.out.print("\033[H\033[2J");
        IO.printMessage(title);
        int i = 0;
        while (i <= listOptions.length) {
            if (i == listOptions.length) {
                break;
            } else {
                IO.printMessage(String.format("( %1d )  %-10s", i + 1, listOptions[i]));
                i++;
            }
        }
        IO.printMessage(String.format("( %1s )  %-10s", "0", exitMessage));
    }

    public void choose() throws InterruptedException {
        int option = IO.readInteger("\nPlease enter a number", 0, 9);
        switch (option) {
            case 1:
                dbay.registerNewUser();
                IO.enterToContinue();
                break;
            case 2:
                try {
                    dbay.logIn();
                } catch (DbayException e) {
                    System.err.println(e.getMessage());
                }
                IO.enterToContinue();
                break;
            case 3:
                dbay.printUsers();
                IO.enterToContinue();
                break;
            case 4:
                dbay.printCars();
                IO.enterToContinue();
                break;
            case 5:
                dbay.printMotorCycles();
                IO.enterToContinue();
                break;
            case 6:
                try {
                    dbay.listNewCar();
                } catch (DbayException e) {
                    System.err.println(e.getMessage());
                }
                IO.enterToContinue();
                break;
            case 7:
                try {
                    dbay.listNewMotorCycle();
                } catch (DbayException e) {
                    System.err.println(e.getMessage());
                }
                IO.enterToContinue();
                break;
            case 8:
                try {
                    dbay.buy();
                } catch (DbayException e) {
                    System.err.println(e.getMessage());
                }
                IO.enterToContinue();
                break;
            case 9:
                try {
                    dbay.logOut();
                } catch (NotLoggedInException e) {
                    System.err.println(e.getMessage());
                }
                IO.enterToContinue();
                break;
            case 0:
                if (IO.getConfirmation("exit")) {
//                dbay.updateUserList();
//                dbay.updateNextItemId();
//                dbay.updateCars();
//                dbay.updateMotorCycles();
                    IO.printMessage("See you later!");
                    System.exit(0);
                    break;
                }
        }
    }
}
