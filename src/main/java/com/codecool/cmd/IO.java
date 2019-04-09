package com.codecool.cmd;

import com.codecool.api.*;
import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class IO {

    private static Scanner reader = new Scanner(System.in);

    IO() {
    }

    public static void printNextId(int nextId) {
        System.out.println("nextId: " + nextId);
    }



    public static void printUserList(List<User> users, String title, String[] headerPositions, String[] headerTitles) {
        System.out.println(title);
        System.out.println();
        for (int i = 0; i < headerTitles.length; i++) {
            System.out.print(String.format(headerPositions[i], headerTitles[i]));
        }
        System.out.println();
        for (User user : users) {
            System.out.println(user);
        }
    }

    public static void printItemByType(List<Item> items, Map<Integer, Dbay.ItemBoughtInfo> boughtItems, Class itemType) {
        if (items.isEmpty()) {
            IO.printMessage("\n   Nothing for sale at the moment. Please check back later.");
        } else {
            System.out.printf("\n%9s %-32s %-15s %4s %11s %9s %7s %7s\n", "  id", "  Name ", "  Body Type ", "  Year ", "  Engine ", "  Doors ", " Gearbox ", " Price ");
            for (Item item : items) {
                if (!boughtItems.containsKey(item.getId()) && itemType.isInstance(item)) {
                    System.out.println(item);
                }
            }
        }
    }

    public static void printMotorCycles(List<MotorCycle> motorCycles, Map<Integer, Dbay.ItemBoughtInfo> boughtItems) {
        if (motorCycles.isEmpty()) {
            IO.printMessage("\n   Nothing for sale at the moment. Please check back later.");
        } else {
            System.out.printf("\n%16s %-32s %-15s %4s %10s %10s\n", "  id", "  Name ", "  Type ", "  Year ", "  Engine ", " Price ");
            for (MotorCycle motorCycle : motorCycles) {
                if (!boughtItems.containsKey(motorCycle.getId())) {
                    System.out.println(motorCycle);
                }
            }
        }
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void enterToContinue() throws InterruptedException {
        Thread.sleep(100);
        System.err.println( "\nPress enter to continue..." );
        String input = reader.nextLine();
    }

    public static boolean getConfirmation(String command) {
        System.out.print( "\n   Are you sure you want to " + command + "? (y or n) " );
        String input = reader.nextLine().toLowerCase();
        return input.equals("y");
    }

    public static int readInteger(String message, int from, int to) {

        while (true) {
            System.out.print(message + ": ");

            try {
                int num = Integer.parseInt(reader.nextLine());
                if (num >= from && num <= to) {
                    return num;
                } else {
                    System.err.println("   The number has to be within [" + from + ".." + to + "]");
                }
            } catch (NumberFormatException e) {
                System.err.println("   You haven't written a proper number.");
            }
        }
    }

    public static double readDouble(String message, double from, double to) {

        while (true) {
            System.out.print(message + ": ");

            try {
                double num = Double.parseDouble(reader.nextLine());
                if (num >= from && num <= to) {
                    return num;
                } else {
                    System.err.println("   The number has to be within [" + from + ".." + to + "]");
                }
            } catch (Exception e) {
                System.err.println("   You haven't written a proper number.");
            }
        }
    }

    public static String readString(String message, String regEx, String invalidFormMessage) {

        while (true) {
            System.out.print(message + ": ");

            try {
                String input = reader.nextLine();
                if (input.matches(regEx)) {
                    return input;
                } else {
                    System.err.println("   The form of the " + message.toLowerCase() + " was improper. " + invalidFormMessage + " Please try again.");
                }
            } catch (Exception e) {
                System.err.println("   You haven't written a proper data.");
            }
        }
    }

    public static boolean chooseIsManual() {
        int input = readInteger("Choose the gearbox type: 1 = Manual or 2 = Automatic ", 1, 2);
        return input == 1;
    }

    public static int chooseItemToBuy() {
        int input = readInteger("\nWhat would like to do?\n   1 = Buy Car\n   2 = Buy Motorcycle\nChoose one", 1, 2);
        return input;
    }

    public static TypeOfCarBody chooseTypeOfCarBody() {
        int i = 0;
        System.out.println("Available choices of body types: ");
        for (TypeOfCarBody type : TypeOfCarBody.values()) {
            System.out.print("   " + ++i + ". ");
            System.out.println(type);
        }

        int input = readInteger("Choose the body type", 1, 7);

        switch (input) {
            case 1:
                return TypeOfCarBody.HACHBACK;
            case 2:
                return TypeOfCarBody.SEDAN;
            case 3:
                return TypeOfCarBody.SUV;
            case 4:
                return TypeOfCarBody.MPV;
            case 5:
                return TypeOfCarBody.COUPE;
            case 6:
                return TypeOfCarBody.CONVERTIBLE;
        }
        return TypeOfCarBody.CROSSOVER;
    }

    public static TypeOfMotorCycle chooseTypeOfMotorCycle() {
        int i = 0;
        System.out.println("Available choices of motorcycle types: ");
        for (TypeOfMotorCycle type : TypeOfMotorCycle.values()) {
            System.out.print("   " + ++i + ". ");
            System.out.println(type);
        }

        int input = readInteger("Choose the type of the motorcycle ", 1, 4);

        switch (input) {
            case 1:
                return TypeOfMotorCycle.CHOPPER;
            case 2:
                return TypeOfMotorCycle.CRUISER;
            case 3:
                return TypeOfMotorCycle.TOURING;
        }
        return TypeOfMotorCycle.ENDURO;
    }
}
