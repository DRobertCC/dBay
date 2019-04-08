package com.codecool.api;

import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

abstract class IO {

    private static Scanner reader = new Scanner(System.in);

    IO() {
    }

    static void printNextId(int nextId) {
        System.out.println("nextId: " + nextId);
    }

    static void printUsers(List<User> users) {
        System.out.printf("\n%-15s %-31s %-31s %-22s\n", "Username", "  Name ", "   Email ", "    Country");
        for (User user : users) {
            System.out.println(user);
        }
    }

    static void printCars(List<Car> cars, Map<Integer, Dbay.ItemBoughtInfo> boughtItems) {
        System.out.printf("\n%9s %-32s %-15s %4s %11s %9s %5s %7s\n", "  id", "  Name ", "  Body Type ", "  Year ", "  Engine ", "  Doors ", " Manual ", " Price ");
        for (Car car : cars) {
            if (!boughtItems.containsKey(car.getId())) {
                System.out.println(car);
            }
        }
    }

    static void printMotorCycles(List<MotorCycle> motorCycles, Map<Integer, Dbay.ItemBoughtInfo> boughtItems) {
        System.out.printf("\n%16s %-32s %-15s %4s %10s %10s\n", "  id", "  Name ", "  Type ", "  Year ", "  Engine ", " Price ");
        for (MotorCycle motorCycle : motorCycles) {
            if (!boughtItems.containsKey(motorCycle.getId())) {
                System.out.println(motorCycle);
            }
        }
    }

    static void printMessage(String message) {
        System.out.println(message);
    }

    static void enterToContinue() throws InterruptedException {
        Thread.sleep(100);
        System.err.println( "\nPress enter to continue..." );
        String input = reader.nextLine();
    }

    static boolean getConfirmation(String command) {
        System.out.println( "Are you sure you want to " + command + "? (y or n)" );
        String input = reader.nextLine();
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
                    System.err.println("   The form of the " + message.toLowerCase() + " was improper. " + invalidFormMessage + " Please try again.");
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

        int input = readInteger("Choose the body type ", 1, 7);

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
}
