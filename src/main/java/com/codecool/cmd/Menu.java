package com.codecool.cmd;


import com.codecool.api.*;
import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;
import com.codecool.api.exeption.*;

import java.util.ArrayList;
import java.util.Calendar;
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
        //items.add(new MotorCycle(3, "Honda CBR", 1998, 5000, 1.2, TypeOfMotorCycle.valueOf("CRUISER"), "Admin"));

        int currentItemId = 3;

        dbay = new Dbay(users, items, currentItemId);
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

        printMenu("\n*** Welcome to dBay. The offline stuff marketplace. ***\n\n " + "           Main menu           Current user: " + dbay.getActiveUserName(), options, "Exit program");
    }

    public void printMenu(String title, String[] listOptions, String exitMessage) {
        System.out.print("\033[H\033[2J");
        System.out.println(title);
        int i = 0;
        while (i <= listOptions.length) {
            if (i == listOptions.length) {
                break;
            } else {
                System.out.println(String.format("( %1d )  %-10s", i + 1, listOptions[i]));
                i++;
            }
        }
        System.out.println(String.format("( %1s )  %-10s", "0", exitMessage));
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
                printAvailableCars();
                IO.enterToContinue();
                break;
            case 5:
                printAvailableMotorCycles();
                IO.enterToContinue();
                break;
            case 6:
                listNewCar();
                IO.enterToContinue();
                break;
            case 7:
                listNewMotorCycle();
                IO.enterToContinue();
                break;
            case 8:
//                try {
//                    dbay.buy();
//                } catch (DbayException e) {
//                    System.err.println("\n   " + e.getMessage());
//                }
                IO.enterToContinue();
                break;
            case 9:
                logOut();
                IO.enterToContinue();
                break;
            case 0:
                if (IO.getConfirmation("exit")) {
//                dbay.updateUserList();
//                dbay.updateNextItemId();
//                dbay.updateCars();
//                dbay.updateMotorCycles();
                    System.out.println("\nSee you later!");
                    System.exit(0);
                    break;
                }
        }
    }

    public void register() {
        System.out.println("\n\n    *** Registration of a new user ***");
        System.out.println("\nPlease give the following details:");
        String userName;
        while (true) {
            boolean free = true;
            userName = IO.readString("Username (or q to cancel)", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z 0-9 ._");
            if (userName.equals("q".toLowerCase())) {
                return;
            }
            try {
                for (User user : dbay.getUsers()) {
                    if (user.getUserName().toLowerCase().equals(userName.toLowerCase())) {
                        free = false;
                        System.err.println("\n   " + "\n   This username is already registered. Choose another one.");
                    }
                }
            } catch (NoRegisteredUsersException e) {
                System.err.println("\n   " + e.getMessage());
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
        System.out.println("\n   " + newUser.getUserName() + " successfully registered.");
    }

    public void logIn() {
        System.out.println("\nPlease give your login details");
        String userName = IO.readString("Username", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z 0-9 ._");
        try {
            dbay.checkRegisteredUserByUserName(userName);
            String password = IO.readString("Password", "(?=.*[a-z]).{6,}", "At least 6 lowercase characters."); // "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"

            try {
                dbay.logIn(userName, password);
                System.out.println("\n   " + userName + " successfully logged in.");
            } catch (NoSuchUserNamePasswordCombinationException e) {
                System.err.println("\n   " + e.getMessage());
            }
        } catch (NotRegisteredException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void printUserList() {
        String title = "                                         Registered users";
        String[] headerPositions = {"%-18s", "%-33s", "%-33s", "%-26s"};
        String[] headerTitles = {"Username", "Name ", "Email ", "Country"};

        try {
            IO.printUserList(dbay.getUsers(), title, headerPositions, headerTitles);
        } catch (NoRegisteredUsersException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void printAvailableCars() {
        try {
            List<Item> cars = new ArrayList<>(dbay.getAvailableCars());

            String title = "                                                Available Cars";
            String[] headerPositions = {"%6s", "%-36s", "%-16s", "%-10s", "%-11s", "%-8s", "%-10s", "%-7s"};
            String[] headerTitles = {"id", "   Name", "Body Type", "Year", "Engine", "Doors", "Gearbox", " Price"};

            IO.printItemByType(cars, title, headerPositions, headerTitles);
        } catch (NothingForSaleAtTheMomentException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void printAvailableMotorCycles() {
        try {
            List<Item> cars = new ArrayList<>(dbay.getAvailableMotorCycles());

            String title = "                                         Available Motorcycles";
            String[] headerPositions = {"%6s", "%-36s", "%-16s", "%-10s", "%-12s", "%-10s"};
            String[] headerTitles = {"id", "   Name", "Type", "Year", "Engine", "Price"};

            IO.printItemByType(cars, title, headerPositions, headerTitles);
        } catch (NothingForSaleAtTheMomentException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void listNewCar() {
        try {
            dbay.checkActiveUser();
            IO.printMessage("\n\n    *** Listing a car for sale ***");
            IO.printMessage("\nPlease give the following details:");
            String name = IO.readString("Name (or q to cancel)", "^[a-zA-Z0-9. ]*", "Only the followings permitted: a-z A-Z . space");
            if (name.equals("q".toLowerCase())) {
                return;
            }
            int yearOfManufacture = IO.readInteger("Manufacturing year", 1900, Calendar.getInstance().get(Calendar.YEAR));
            double price = IO.readDouble("Price", 0, 10000000);
            double engineSize = IO.readDouble("Engine size", 0, 15);
            int numberOfDoors = IO.readInteger("Number of doors", 2, 10);
            TypeOfCarBody typeOfCarBody = IO.chooseTypeOfCarBody();
            boolean isManual = IO.chooseIsManual();

            try {
                dbay.addCar(name, yearOfManufacture, price, engineSize, numberOfDoors, typeOfCarBody, isManual);
                IO.printMessage("\n   " + name + " successfully added.");
            } catch (AlreadyListedException e) {
                System.err.println("\n   " + e.getMessage());
            }

        } catch (NotLoggedInException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void listNewMotorCycle() {
        try {
            dbay.checkActiveUser();
            IO.printMessage("\n *** Listing a motorcycle for sale ***");
            IO.printMessage("\nPlease give the following details:");
            String name = IO.readString("Name (or q to cancel)", "^[a-zA-Z0-9. ]*", "Only the followings permitted: a-z A-Z . space");
            if (name.equals("q".toLowerCase())) {
                return;
            }
            int yearOfManufacture = IO.readInteger("Manufacturing year", 1900, Calendar.getInstance().get(Calendar.YEAR));
            double price = IO.readDouble("Price", 0, 10000000);
            double engineSize = IO.readDouble("Engine size", 0, 10);
            TypeOfMotorCycle typeOfMotorCycle = IO.chooseTypeOfMotorCycle();
            String listedBy = "Admin"; //activeUser.getUserName();

            try {
                dbay.addMotorCycle(name, yearOfManufacture, price, engineSize, typeOfMotorCycle);
                IO.printMessage("\n   " + name + " successfully added.");
            } catch (AlreadyListedException e) {
                System.err.println("\n   " + e.getMessage());
            }

        } catch (NotLoggedInException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void buyItem() {

    }

    public void logOut() {
        if (dbay.getActiveUserName().equals("-")) {
            System.err.println("\n   Nobody is logged in!");
        } else {
            if (IO.getConfirmation("logout")) {
                try {
                    dbay.logOut();
                    System.out.println("\n   You have logged out.");
                } catch (NotLoggedInException e) {
                    System.err.println("\n   " + e.getMessage());
                }
            }
        }
    }
}
