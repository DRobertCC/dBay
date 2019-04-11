package com.codecool.api;

import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;
import com.codecool.api.exeption.*;
import com.codecool.cmd.IO;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dbay {

    public static class ItemBoughtInfo implements Serializable{ // Inner, rejtett osztály, csak itt érhető el.

        User user;
        LocalDateTime date;

        ItemBoughtInfo(User user, LocalDateTime date) {
            this.user = user;
            this.date = date;
        }
    }

    private static int currentItemId = 0;
//    private static int lastSavedUserId;
//    private static int lastSavedItemId;

    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private Map<Integer, ItemBoughtInfo> boughtItems = new HashMap<>(); // Which Item (id) was bought by which User (userName) and when.

    private User activeUser = null; // will change after login
    private String databasePath;

    public Dbay(String databasePath) {
        this.databasePath = databasePath;
        deSerializeDatabase();
    }

//    Dbay() {
//        currentItemId = XMLLoader.getnextItemId("data/Dbay.xml");
//        lastSavedItemId = currentItemId;
//        users = new ArrayList<>(XMLLoader.getUsers("data/Users.xml"));
//        lastSavedUserId = users.size();
//        cars = new ArrayList<>(XMLLoader.getCars("data/Dbay.xml"));
//        motorCycles = new ArrayList<>(XMLLoader.getMotorCycles("data/Dbay.xml"));
//    }

    public void serializeDatabase() {
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(databasePath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of objects
            out.writeObject(currentItemId);
            out.writeObject(users);
            out.writeObject(items);
            out.writeObject(boughtItems);

            out.close();
            file.close();

            IO.printMessage("\nDatabase successfully saved to " + databasePath);

        } catch (IOException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    private void deSerializeDatabase() {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(databasePath);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            currentItemId = (int) in.readObject();
            users = (List<User>) in.readObject();
            items = (List<Item>) in.readObject();
            boughtItems = (Map<Integer, ItemBoughtInfo>) in.readObject();

            in.close();
            file.close();

            IO.printMessage("\nDatabase successfully loaded from " + databasePath);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\n   " + e.getMessage());
        }
    }

    public void registerNewUser(User user) throws AlreadyRegisteredException {
        if (users.contains(user)) {
            throw new AlreadyRegisteredException("Already registered");
        }
        users.add(user);
    }

    public void logIn(String userName, String password) throws NoSuchUserNamePasswordCombinationException {
        for (User user : users) {
            if (user.getUserName().toLowerCase().equals(userName.toLowerCase()) && user.getPassword().equals(password)) {
                activeUser = user;
                break;
            }
        }
        if (activeUser == null) {
            throw new NoSuchUserNamePasswordCombinationException("Wrong username and password combination");
        }
    }

    public List<User> getUsers() throws NoRegisteredUsersException {
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NoRegisteredUsersException("No registered users");
        }
    }

    public List<Item> getAvailableCars() throws NothingForSaleAtTheMomentException {
        List<Item> cars = new ArrayList<>();
        if (!items.isEmpty()) {
            for (Item item : items) {
                if (!boughtItems.containsKey(item.getId()) && item instanceof Car) {
                    cars.add(item);
                }
            }
        } else {
            throw new NothingForSaleAtTheMomentException("Nothing for sale at the moment. Please check back later.");
        }
        if (cars.isEmpty()) {
            throw new NothingForSaleAtTheMomentException("No Cars for sale at the moment. Please check back later.");
        }
        return cars;
    }

    public List<Item> getAvailableMotorCycles() throws NothingForSaleAtTheMomentException {
        List<Item> motorCycles = new ArrayList<>();
        if (!items.isEmpty()) {
            for (Item item : items) {
                if (!boughtItems.containsKey(item.getId()) && item instanceof MotorCycle) {
                    motorCycles.add(item);
                }
            }
        } else {
            throw new NothingForSaleAtTheMomentException("Nothing for sale at the moment. Please check back later.");
        }
        if (motorCycles.isEmpty()) {
            throw new NothingForSaleAtTheMomentException("No MotorCycles for sale at the moment. Please check back later.");
        }
        return motorCycles;
    }

    public void addCar(String name, int yearOfManufacture, double price, double engineSize, int numberOfDoors, TypeOfCarBody typeOfCarBody, boolean isManual) throws AlreadyListedException, NotLoggedInException {
        checkActiveUser();
        Car newCar = new Car(++currentItemId, name, yearOfManufacture, price, engineSize, numberOfDoors, typeOfCarBody, isManual, activeUser.getUserName());

        if (items.contains(newCar)) {
            --currentItemId;
            throw new AlreadyListedException("We already have a Car with the same details listed by you!");
        }
        items.add(newCar);
    }

    public void addMotorCycle(String name, int yearOfManufacture, double price, double engineSize, TypeOfMotorCycle typeOfMotorCycle) throws AlreadyListedException, NotLoggedInException {
        checkActiveUser();
        MotorCycle newMotorCycle = new MotorCycle(++currentItemId, name, yearOfManufacture, price, engineSize, typeOfMotorCycle, activeUser.getUserName());
        if (items.contains(newMotorCycle)) {
            throw new AlreadyListedException("We already have a Motorcycle with the same details listed by you!");
        }
        items.add(newMotorCycle);
    }

    public Map<Integer, ItemBoughtInfo> getBoughtItems() {
        return boughtItems;
    }

    public void buyItemByItemId(int itemId) throws DbayException {
        checkActiveUser();
        checkNotBought(itemId);
        boughtItems.put(itemId, new ItemBoughtInfo(activeUser, LocalDateTime.now()));
    }

    public void logOut() throws NotLoggedInException {
        if (activeUser != null) {
            activeUser = null;
        } else {
            throw new NotLoggedInException("Nobody is logged in!");
        }
    }

    public String getActiveUserName() {
        if (this.activeUser == null) {
            return "-";
        }
        return this.activeUser.getUserName();
    }

//    public static int getcurrentItemId() {
//        return currentItemId;
//    }

    public void checkRegisteredUserByUserName(String userName) throws NotRegisteredException {
        boolean isRegistered = false;
        for (User user : users) {
            if (user.getUserName().toLowerCase().equals(userName.toLowerCase())) {
                isRegistered = true;
            }
        }
        if (!isRegistered) {
            throw new NotRegisteredException("No such username! Please register first.");
        }
    }

    public void checkActiveUser() throws NotLoggedInException {
        if (activeUser == null) {
            throw new NotLoggedInException("You must be logged in to post new listings or buy anything!");
        }
    }

    private void checkNotBought(int itemId) throws AlreadyBoughtException {
        if (boughtItems.containsKey(itemId)) {
            ItemBoughtInfo info = boughtItems.get(itemId); // Az itemId-hez tarozó rekord letárolása. Ezáltal hozzáférek a fieldjeihez:
            throw new AlreadyBoughtException("id: " + itemId + " already bought at " + info.date);
        }
    }

    public boolean checkItemIdInItemList(List<Item> items, int itemId) {
        boolean result = false;
        for (Item item : items) {
            if (item.getId() == itemId) {
                result = true;
            }
        }
        return result;
    }

//    public public void printNextId() {
//        IO.printNextId(currentItemId);

//    }
//    public void updateUserList() {
//        XMLWriter.updateUserListInXML(users, "data/Users.xml", lastSavedUserId);
//    }
//
//    public void updateCars() {
//        XMLWriter.updateCarsInXML(cars, "data/Dbay.xml", lastSavedItemId);
//    }
//
//    public void updateMotorCycles() {
//        XMLWriter.updateMotorCyclesInXML(motorCycles, "data/Dbay.xml", lastSavedItemId);
//    }
//
//    public void updateNextItemId() {
//        XMLWriter.updateCurrentItemIdInXML("data/Dbay.xml", currentItemId);

//    }
}

