package com.codecool.api;

import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;
import com.codecool.api.exeption.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dbay {

    public static class ItemBoughtInfo { // Inner, rejtett osztály, csak itt érhető el.

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

    private List<User> users;
    private List<Item> items;
    private Map<Integer, ItemBoughtInfo> boughtItems = new HashMap<>(); // Which Item (id) was bought by which User (userName) and when.
    private User activeUser = null; // will change after login

    public Dbay(List<User> users, List<Item> items, int id) {
        this.users = users;
        this.items = items;
        currentItemId = id;
    }

//    Dbay() {
//        currentItemId = XMLLoader.getnextItemId("data/Dbay.xml");
//        lastSavedItemId = currentItemId;
//        users = new ArrayList<>(XMLLoader.getUsers("data/Users.xml"));
//        lastSavedUserId = users.size();
//        cars = new ArrayList<>(XMLLoader.getCars("data/Dbay.xml"));
//        motorCycles = new ArrayList<>(XMLLoader.getMotorCycles("data/Dbay.xml"));
//    }

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

/*
    public void buy() throws DbayException {
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
*/

//        for (Item item : items) {
//            if ( item.getId().equals(itemId) ) {
//                ;
//            }
//        }
/*
    }
*/

    public void logOut() throws NotLoggedInException {
        if (activeUser != null) {
            activeUser = null;
        } else {
            throw new NotLoggedInException("Nobody is logged in!");
        }
    }

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

    public String getActiveUserName() {
        if (this.activeUser == null) {
            return "-";
        }
        return this.activeUser.getUserName();
    }

/*
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
*/

    private void checkNotBought(int itemId) throws AlreadyBoughtException {
        if (boughtItems.containsKey(itemId)) {
            ItemBoughtInfo info = boughtItems.get(itemId); // Az itemId-hez tarozó rekord letárolása. Ezáltal hozzáférek a fieldjeihez:
            throw new AlreadyBoughtException("id: " + itemId + " already bought at " + info.date);
        }
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

    public static int getcurrentItemId() {
        return currentItemId;
    }
}

