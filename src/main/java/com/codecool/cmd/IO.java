package com.codecool.cmd;

import com.codecool.api.Item;
import com.codecool.api.User;
import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfItems;
import com.codecool.api.enums.TypeOfMotorCycle;

import java.util.List;
import java.util.Scanner;

public abstract class IO {

    private static Scanner reader = new Scanner(System.in);

    IO() {
    }

/*
    public static void printNextId(int nextId) {
        System.out.println("nextId: " + nextId);
    }
*/

    static void printUserList(List<User> users, String title, String[] headerPositions, String[] headerTitles) {
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

    static void printItemList(List<Item> items, String title, String[] headerPositions, String[] headerTitles) {
        System.out.println(title);
        System.out.println();
        for (int i = 0; i < headerTitles.length; i++) {
            System.out.print(String.format(headerPositions[i], headerTitles[i]));
        }
        System.out.println();
        for (Item item : items) {
            System.out.println(item);
        }
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    static void enterToContinue() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("\nPress enter to continue...");
        String input = reader.nextLine();
    }

    static boolean getConfirmation(String command) {
        String input = IO.readString("\nAre you sure you want to " + command + "? (y or n)", "^[yn]", "Only type y or n");
        return input.equals("y");
    }

    static int readInteger(String message, int from, int to) {

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

    static double readDouble(String message, double from, double to) {

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

    static String readString(String message, String regEx, String invalidFormMessage) {

        while (true) {
            System.out.print(message + ": ");

            try {
                String input = reader.nextLine();
                if (input.matches(regEx)) {
                    return input;
                } else {
                    System.err.println(invalidFormMessage + " Please try again.");
                }
            } catch (Exception e) {
                System.err.println("   You haven't written a proper data.");
            }
        }
    }

    static boolean chooseIsManual() {
        int input = readInteger("Choose the gearbox type: 1 = Manual or 2 = Automatic ", 1, 2);
        return input == 1;
    }

    static TypeOfCarBody chooseTypeOfCarBody() {
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

    static TypeOfMotorCycle chooseTypeOfMotorCycle() {
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

    static int chooseTypeOfItems(String message) {
        int i = 0;
        System.out.println(message);
        for (TypeOfItems type : TypeOfItems.values()) {
            System.out.print("   " + ++i + ". ");
            System.out.println(type);
        }

        int input = readInteger("Choose the type of the item", 1, 2);

        switch (input) {
            case 1:
                return 1;
        }
        return 2;
    }


}
