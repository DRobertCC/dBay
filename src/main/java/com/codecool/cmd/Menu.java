package com.codecool.cmd;


import com.codecool.api.*;
import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;
import com.codecool.api.exeption.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class Menu {

    private static Dbay dbay; // Lehet statikus, hiszen csak egy lesz belőe per dbay. Így nem kell argumentumként átadni sem minden metódusnak.

    Menu() {
//        List<User> users = new ArrayList<>();
//        List<Item> items = new ArrayList<>();
//
//        users.add(new User("Admin", "kecske", "John Hopkins", "iaia@mail.com", "Italy"));
//        users.add(new User("Joe", "kecske", "Mekk Elek", "johny@mail.com", "UK"));
//
//        items.add(new Car(1, "BMW M5", 2012, 4990.0, 1.5, 5, TypeOfCarBody.valueOf("HACHBACK"), true, "Admin"));
//        items.add(new Car(2, "Ford Mustang", 2000, 9999, 5, 2, TypeOfCarBody.valueOf("COUPE"), false, "Admin"));
//        items.add(new MotorCycle(3, "Honda CBR", 1998, 5000, 1.2, TypeOfMotorCycle.valueOf("CRUISER"), "Admin"));
//        int currentItemId = 3;
//        dbay = new DbayCountry(users, items, currentItemId, "UK");

        dbay = new DbayCountry("src/main/resources/Dbay.dat", "UK");
    }

    void handleMenu() {

        String[] options = {
                "Register",
                "Log in",
                "Show registered users",
                "Show list of available items",
                "Add new item for sale",
                "Buy an item",
                "Show the items I already bought",
                "Log out"};

        printMenu("\n*** Welcome to dBay " + ((DbayCountry) dbay).getCountry() + ". The offline stuff marketplace. ***\n\n " + "           Main menu           Current user: " + dbay.getActiveUserName(), options, "Exit program");
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

    void choose() throws InterruptedException {
        int option = IO.readInteger("\nPlease enter a number", 0, 8);
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
                int typeOfItems = IO.chooseTypeOfItems("\nWhat type of items are you looking for?");
                switch (typeOfItems) {
                    case 1:
                        printAvailableCars();
                        IO.enterToContinue();
                        break;
                    case 2:
                        printAvailableMotorCycles();
                        IO.enterToContinue();
                        break;
                }
                break;
            case 5:
                try {
                    dbay.checkActiveUser();
                    typeOfItems = IO.chooseTypeOfItems("\nWhat type of item do you want to list for sale?");
                    switch (typeOfItems) {
                        case 1:
                            listNewCar();
                            IO.enterToContinue();
                            break;
                        case 2:
                            listNewMotorCycle();
                            IO.enterToContinue();
                            break;
                    }
                } catch (NotLoggedInException e) {
                    System.err.println("\n   " + e.getMessage());
                    IO.enterToContinue();
                }
                break;
            case 6:
                buyItem();
                IO.enterToContinue();
                break;
            case 7:
                showBoughtItems();
                IO.enterToContinue();
                break;
            case 8:
                logOut();
                IO.enterToContinue();
                break;
            case 0:
                if (IO.getConfirmation("exit")) {
                    try {
                        dbay.logOut();
                    } catch (NotLoggedInException e) {
                    }
//                dbay.updateUserList();
//                dbay.updateNextItemId();
//                dbay.updateCars();
//                dbay.updateMotorCycles();
                    dbay.serializeDatabase();
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
            userName = IO.readString("Username (or q to cancel)", "(^[a-zA-Z0-9._]).{0,}", "   Only the followings permitted: a-z A-Z 0-9 ._");
            if (userName.equals("q".toLowerCase())) {
                return;
            }
            if (!dbay.isRegisteredUser(userName)) {
                break;
            } else {
                System.err.println("   This username is already registered. Choose another one!\n");
            }
        }
// Real life validations:
        String password = IO.readString("Password", "(^[a-z]).{5,}", "   At least 6 characters.");
        String fullName = IO.readString("Full name", "(^[a-zA-Z ]).{2,}", "   Only the followings permitted (at least 3 char): a-z A-Z space.");
        String email = IO.readString("Email address", "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", "Invalid email format.");
        String country = IO.readString("Country of residence", "(^[a-zA-Z0-9._]).{1,}", "   Only the followings permitted (at least 2 char): a-z A-Z space.");
//        String password = IO.readString("Password", "(^[a-z]).{5,}", "   At least 6 lowercase characters.");
//        String fullName = IO.readString("Full name", "(?=.*[a-zA-Z ]).{3,}", "   Only the followings permitted: a-z A-Z space");
//        String email = IO.readString("Email address", "^[a-zA-Z0-9._]*", "");
//        String country = IO.readString("Country of residence", "(?=.*[a-zA-Z0-9._]).{2,}", "   Only the followings permitted: a-z A-Z space");
        User newUser = new User(userName, password, fullName, email, country);

        try {
            dbay.registerNewUser(newUser);
            System.out.println("\n   " + newUser.getUserName() + " successfully registered.");
        } catch (NotAvailableInYourCountryException e) {
            System.err.println(e.getMessage());
        } catch (DbayException e) {
            System.err.println(e.getMessage() + ": " + newUser.getUserName());
        }
    }

    public void logIn() {
        System.out.println("\nPlease give your login details");
        String userName = IO.readString("Username", "^[a-zA-Z0-9._]*", "");
        if (dbay.isRegisteredUser(userName)) {
            String password = IO.readString("Password", "^[a-zA-Z0-9_!#$%&'*+/=?]*", ""); // "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"

            try {
                dbay.logIn(userName, password);
                System.out.println("\n   " + userName + " successfully logged in.");
            } catch (NoSuchUserNamePasswordCombinationException e) {
                System.err.println("\n   " + e.getMessage());
            }
        } else {
            System.err.println("\n   No such username! Please register first.");
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

            IO.printItemList(cars, title, headerPositions, headerTitles);
        } catch (NothingForSaleAtTheMomentException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void printAvailableMotorCycles() {
        try {
            List<Item> motorCycles = new ArrayList<>(dbay.getAvailableMotorCycles());

            String title = "                                         Available Motorcycles";
            String[] headerPositions = {"%6s", "%-36s", "%-16s", "%-10s", "%-12s", "%-10s"};
            String[] headerTitles = {"id", "   Name", "Type", "Year", "Engine", "Price"};

            IO.printItemList(motorCycles, title, headerPositions, headerTitles);
        } catch (NothingForSaleAtTheMomentException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void listNewCar() {
        IO.printMessage("\n\n    *** Listing a car for sale ***");
        IO.printMessage("\nPlease give the following details:");
        String name = IO.readString("Name (or q to cancel)", "(^[a-zA-Z0-9._]).{1,}", "Only the followings permitted: a-z A-Z . space");
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
        } catch (DbayException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void listNewMotorCycle() {
        IO.printMessage("\n *** Listing a motorcycle for sale ***");
        IO.printMessage("\nPlease give the following details:");
        String name = IO.readString("Name (or q to cancel)", "(^[a-zA-Z0-9._]).{1,}", "Only the followings permitted: a-z A-Z . space");
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
        } catch (DbayException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void buyItem() {
        try {
            dbay.checkActiveUser();
            int choose = IO.chooseTypeOfItems("\nWhat would like to buy?");
            int itemId = 0;
            List<Item> availableItems = new ArrayList<>();

            switch (choose) {
                case 1:
                    try {
                        availableItems = dbay.getAvailableCars();
                        printAvailableCars();
                        itemId = IO.readInteger("\nPlease enter the id of the car you would like to buy", 1, 999999);
                        if (!dbay.checkItemIdInItemList(availableItems, itemId)) {
                            IO.printMessage("\nNo Car for sale with such id!");
                            return;
                        }
                    } catch (NothingForSaleAtTheMomentException e) {
                        System.err.println("\n   " + e.getMessage());
                        return;
                    }
                    break;
                case 2:
                    try {
                        availableItems = dbay.getAvailableMotorCycles();
                        printAvailableMotorCycles();
                        itemId = IO.readInteger("\nPlease enter the id of the motorcycle you would like to buy", 1, 999999);
                        if (!dbay.checkItemIdInItemList(availableItems, itemId)) {
                            IO.printMessage("\nNo Motorcycle for sale with such id!");
                            return;
                        }
                    } catch (NothingForSaleAtTheMomentException e) {
                        System.err.println("\n   " + e.getMessage());
                        return;
                    }
                    break;
            }

            String itemName = "";
            double itemPrice = 0;
            for (Item item : availableItems) {
                if (item.getId() == itemId) {
                    itemName = item.getName();
                    itemPrice = item.getPrice();
                }
            }

            if (IO.getConfirmation("buy this item: " + itemName + " for €" + itemPrice)) {
                try {
                    dbay.buyItemByItemId(itemId);
                    IO.printMessage("\n   Congratulations. the " + itemName + " is yours!");
                } catch (DbayException e) {
                    System.err.println("\n   " + e.getMessage());
                }
            } else {
                IO.printMessage("Nothing bought.");
            }
        } catch (NotLoggedInException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void showBoughtItems() {
        try {
            List<Item> boughtItems = dbay.getBoughtItemsByCurrentUser();
            IO.printMessage("\n                                     Items bought by " + dbay.getActiveUserName() + "\n");
            for (Item item : boughtItems) {
                System.out.println(item);
            }
        } catch (NoSuchItemException e) {
            System.err.println("\n   You haven't bought anything yet.");
        } catch (NotLoggedInException ex) {
            System.err.println("\n   Please log in first!");
        }
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
