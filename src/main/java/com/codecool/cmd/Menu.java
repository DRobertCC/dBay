package com.codecool.cmd;


import com.codecool.api.*;
import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;
import com.codecool.api.exeption.AlreadyRegisteredException;
import com.codecool.api.exeption.DbayException;
import com.codecool.api.exeption.NoSuchUserNamePasswordCombinationException;
import com.codecool.api.exeption.NotLoggedInException;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private static Dbay dbay; // Lehet statikus, hiszen csak egy lesz belőe per dbay. Így nem kell argumentumként átadni sem minden metódusnak.

    public Menu() {
        List<User> users = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        users.add(new User("Admin", "kecske", "John Hopkins", "iaia@mail.com", "Italy"));
        users.add(new User("Joe", "kecske", "Mekk Elek", "johny@mail.com", "UK"));

        items.add(new Car(1, "BMW M5", 2012, 4990.0, 1.5, 5, TypeOfCarBody.valueOf("HACHBACK"), true, "Admin"));
        items.add(new Car(2, "Ford Mustang", 2000, 9999, 5, 2, TypeOfCarBody.valueOf("COUPE"), false, "Admin"));
        items.add(new MotorCycle(3, "Honda CBR", 1998, 5000, 1.2, TypeOfMotorCycle.valueOf("CRUISER"), "Admin"));

        int currentIemId = 3;

        dbay = new Dbay(users, items, currentIemId);
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
                register();
                IO.enterToContinue();
                break;
            case 2:
                logIn();
                IO.enterToContinue();
                break;
            case 3:
                printUserList();
                IO.enterToContinue();
                break;
            case 4:
                //printCars();
                IO.enterToContinue();
                break;
            case 5:
                //printMotorCycles();
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
//                try {
//                    dbay.buy();
//                } catch (DbayException e) {
//                    System.err.println(e.getMessage());
//                }
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

    public void register() {
        IO.printMessage("\n\n    *** Registration of a new user ***");
        IO.printMessage("\nPlease give the following details:");
        String userName;
        while (true) {
            boolean free = true;
            userName = IO.readString("Username", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z 0-9 ._");
            for (User user : dbay.getUsers()) {
                if (user.getUserName().toLowerCase().equals(userName.toLowerCase())) {
                    free = false;
                    IO.printMessage("\n   " + "\n   This username is already registered. Choose another one.");
                }
            }
            if (free) {
                break;
            }
        }

//    String password = IO.readString("Password", "(?=.*[a-z]).{6,}", "At least 6 lowercase characters.");
//    String fullName = IO.readString("Full name", "^[a-zA-Z ]*", "Only the followings permitted: a-z A-Z space");
//    String email = IO.readString("Email address", "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", "");
//    String country = IO.readString("Country of residence", "^[a-zA-Z ]*", "Only the followings permitted: a-z A-Z space");
        String password = IO.readString("Password", "^[a-zA-Z0-9._]*", "At least 6 lowercase characters.");
        String fullName = IO.readString("Full name", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z space");
        String email = IO.readString("Email address", "^[a-zA-Z0-9._]*", "");
        String country = IO.readString("Country of residence", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z space");
        User newUser = new User(userName, password, fullName, email, country);

        try {
            dbay.registerNewUser(newUser);
        } catch (AlreadyRegisteredException e) {
            System.err.println(e.getMessage() + ": " + newUser.getUserName());
        }
        IO.printMessage("\n   " + newUser.getUserName() + " successfully registered.");
    }

    public void logIn() {
        IO.printMessage("\nPlease give your login details");
        String userName = IO.readString("Username", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z 0-9 ._");
        String password = IO.readString("Password", "(?=.*[a-z]).{6,}", "At least 6 lowercase characters."); // "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"

        try {
            dbay.logIn(userName, password);
        } catch (NoSuchUserNamePasswordCombinationException e) {
            System.err.println(e.getMessage());
        }
        IO.printMessage("\n   " + userName + " successfully logged in.");
    }

    public void printUserList() {
        String title = "                                         Registered users";
        String[] headerPositions = {"%-18s", "%-33s", "%-33s", "%-26s"};
        String[] headerTitles = {"Username", "Name ", "Email ", "Country"};

        IO.printUserList(dbay.getUsers(), title, headerPositions, headerTitles);
    }

//    public void printCars() {
//        IO.printMessage("                                                Available Cars");
//        IO.printItemByType(dbay.getItems(), dbay.getBoughtItems()));
//    }
//
//    public void printMotorCycles() {
//        IO.printMessage("                                            Available Motorcycles");
//        IO.printItemByType(dbay.getItems(), dbay.getBoughtItems());
//    }


























}
