package com.codecool.api;

import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;
import com.codecool.api.exeption.*;

import java.time.LocalDateTime;
import java.util.*;

public class Dbay {

    static class ItemBoughtInfo { // Inner, rejtett osztály, csak itt érhető el.

        User user;

        LocalDateTime date;

        ItemBoughtInfo(User user, LocalDateTime date) {
            this.user = user;
            this.date = date;
        }
    }

    private static int nextItemId = 0;
    //private static int nextUserID = 0;

    private static int lastSavedItemId;
    private static int lastSavedUserId;
    private List<User> users;
    private List<Car> cars;
    private List<MotorCycle> motorCycles;
    private Map<Integer, ItemBoughtInfo> boughtItems = new HashMap<>(); // Which Item (id) was bought by which User (userName) and when.
    private User activeUser = null; // will change after login

    Dbay() {
        nextItemId = XMLLoader.getnextItemId("data/Dbay.xml");
        lastSavedItemId = nextItemId;
        users = new ArrayList<>(XMLLoader.getUsers("data/Users.xml"));
        lastSavedUserId = users.size();
        cars = new ArrayList<>(XMLLoader.getCars("data/Dbay.xml"));
        motorCycles = new ArrayList<>(XMLLoader.getMotorCycles("data/Dbay.xml"));
    }

    void registerNewUser() {
        IO.printMessage("\n\n    *** Registration of a new user ***");
        IO.printMessage("\nPlease give the following details:");
        String userName;
        while (true) {
            boolean free = true;
            userName = IO.readString("Username", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z 0-9 ._");
            for (User user : users) {
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
        users.add(newUser);
        IO.printMessage("\n   " + newUser.getUserName() + " successfully registered.");
    }


    void logIn() throws NoSuchUserNamePasswordCombinationException {
        IO.printMessage("\nPlease give your login details");
        String userName = IO.readString("Username", "^[a-zA-Z0-9._]*", "Only the followings permitted: a-z A-Z 0-9 ._");
        String password = IO.readString("Password", "(?=.*[a-z]).{6,}", "At least 6 lowercase characters."); // "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"
        for (User user : users) {

            if (user.getUserName().toLowerCase().equals(userName.toLowerCase()) && user.getPassword().equals(password)) {
                activeUser = user;
                IO.printMessage("\n   " + userName + " successfully logged in.");
                break;
            }
        }
        if (activeUser == null) {
            throw new NoSuchUserNamePasswordCombinationException("\n   Wrong username and/or password");
        }
    }

    void listNewCar() throws DbayException {
        checkActiveUser();
        IO.printMessage("\n\n    *** Listing a car for sale ***");
        IO.printMessage("\nPlease give the following details:");
        String name = IO.readString("Name", "^[a-zA-Z0-9. ]*", "Only the followings permitted: a-z A-Z . space");
        int yearOfManufacture = IO.readInteger("Manufacturing year", 1900, Calendar.getInstance().get(Calendar.YEAR));
        double price = IO.readDouble("Price", 0, 10000000);
        double engineSize = IO.readDouble("Engine size", 0, 15);
        int numberOfDoors = IO.readInteger("Number of doors", 2, 10);
        TypeOfCarBody typeOfCarBody = IO.chooseTypeOfCarBody();
        boolean isManual = IO.chooseIsManual();
        String listedBy = "Admin"; //activeUser.getUserName();
        Car newCar = new Car(++nextItemId, name, yearOfManufacture, price, engineSize, numberOfDoors, typeOfCarBody, isManual, listedBy);
        if (cars.contains(newCar)) {
            throw new AlreadyListedException("\n   We already have a car with the same details!");
        }
        cars.add(newCar);
        IO.printMessage("\n   " + newCar.getName() + " successfully added.");
    }

    void listNewMotorCycle() throws DbayException {
        checkActiveUser();
        IO.printMessage("\n\n    *** Listing a motorcycle for sale ***");
        IO.printMessage("\nPlease give the following details:");
        String name = IO.readString("Name", "^[a-zA-Z0-9. ]*", "Only the followings permitted: a-z A-Z . space");
        int yearOfManufacture = IO.readInteger("Manufacturing year", 1900, Calendar.getInstance().get(Calendar.YEAR));
        double price = IO.readDouble("Price", 0, 10000000);
        double engineSize = IO.readDouble("Engine size", 0, 15);
        TypeOfMotorCycle typeOfMotorCycle = IO.chooseTypeOfMotorCycle();
        String listedBy = "Admin"; //activeUser.getUserName();
        MotorCycle newMotorCycle = new MotorCycle(++nextItemId, name, yearOfManufacture, price, engineSize, typeOfMotorCycle, listedBy);
        if (motorCycles.contains(newMotorCycle)) {
            throw new AlreadyListedException("\n   We already have a bike with the same details!");
        }
        motorCycles.add(newMotorCycle);
        IO.printMessage("   " + newMotorCycle.getName() + " successfully added.");
    }

    void buy() throws DbayException {
        //checkRegisteredUser(activeUser);
        //checkActiveUser();
        int choose = IO.chooseItemToBuy();
        int maxId = 0;
        int itemId = 0;
        switch (choose) {
            case 1:
                printCars();
                maxId = cars.get(cars.size() - 1).getId();
                do {
                    itemId = IO.readInteger("\nPlease enter the id of the car you would like to buy", 1, maxId);
                } while (isThisCarAvailableToBuy(itemId));
                break;
            case 2:
                printMotorCycles();
                maxId = motorCycles.get(motorCycles.size() - 1).getId();
                do {
                    itemId = IO.readInteger("\nPlease enter the id of the motorcycle you would like to buy", 1, maxId);
                } while (isThisMotorCycleAvailableToBuy(itemId));

                break;
        }

        checkNotBought(itemId);

        String itemName = "";
        double itemPrice = 0;
        switch (choose) {
            case 1:
                for (Car car : cars) {
                    if (car.getId() == itemId) {
                        itemName = car.getName();
                        itemPrice = car.getPrice();
                    }
                }
                break;
            case 2:
                for (MotorCycle motorCycle : motorCycles) {
                    if (motorCycle.getId() == itemId) {
                        itemName = motorCycle.getName();
                        itemPrice = motorCycle.getPrice();
                    }
                }
                break;
        }

        if (IO.getConfirmation("buy this item: " + itemName + " for €" + itemPrice)) {
            boughtItems.put(itemId, new ItemBoughtInfo(activeUser, LocalDateTime.now())); // Ha eddig eljutott, akkor vásárolhat.
            IO.printMessage("\n   Congratulations. the " + itemName + " is yours!");
        } else {
            IO.printMessage("Nothing bought.");
        }

//        for (Item item : items) {
//            if ( item.getId().equals(itemId) ) {
//                ;
//            }
//        }
    }

    void logOut() throws NotLoggedInException {
        if (activeUser != null) {
            if (IO.getConfirmation("logout")) {
                activeUser = null;
                IO.printMessage("\n   You have logged out.");
            }
        } else {
            throw new NotLoggedInException("\nNobody was logged in!");
        }
    }

    private void checkRegisteredUser(User user) throws NotRegisteredException {
        if (!users.contains(user)) {
            throw new NotRegisteredException("\n   Please register first.");
        }
    }

    private void checkActiveUser() throws NotLoggedInException {
        if (activeUser == null) {
            throw new NotLoggedInException("\n   You must be logged in to post new listings or buy anything!");
        }
    }

    private boolean isThisCarAvailableToBuy(int itemId) {
        int i = 0;
        while (i < cars.size()) {
            if (cars.get(i).getId() == itemId) {
                return true;
            }
        }
        return false;
    }

    private boolean isThisMotorCycleAvailableToBuy(int itemId) {
        int i = 0;
        while (i < motorCycles.size()) {
            if (motorCycles.get(i).getId() == itemId) {
                return true;
            }
        }
        return false;
    }

    private void checkNotBought(int itemId) throws AlreadyBoughtException {
        if (boughtItems.containsKey(itemId)) {
            ItemBoughtInfo info = boughtItems.get(itemId); // Az itemId-hez tarozó rekord letárolása. Ezáltal hozzáférek a fieldjeihez:
            throw new AlreadyBoughtException("\n   id: " + itemId + " already bought at " + info.date);
        }
    }

//    public void printNextId() {
//        IO.printNextId(nextItemId);
//    }

    void printUsers() {
        IO.printUsers(users);
    }

    void printCars() {
        IO.printMessage("                                                Available Cars");
        IO.printCars(cars, boughtItems);
    }

    void printMotorCycles() {
        IO.printMessage("                                            Available Motorcycles");
        IO.printMotorCycles(motorCycles, boughtItems);
    }

    public void updateUserList() {
        XMLWriter.updateUserListInXML(users, "data/Users.xml", lastSavedUserId);
    }

    public void updateCars() {
        XMLWriter.updateCarsInXML(cars, "data/Dbay.xml", lastSavedItemId);
    }

    public void updateMotorCycles() {
        XMLWriter.updateMotorCyclesInXML(motorCycles, "data/Dbay.xml", lastSavedItemId);
    }

    public void updateNextItemId() {
        XMLWriter.updateNextItemIdInXML("data/Dbay.xml", nextItemId);
    }

    String getActiveUser() {
        if (this.activeUser == null) {
            return "-";
        }
        return this.activeUser.getUserName();
    }
}