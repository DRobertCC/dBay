package com.codecool.api;

import com.codecool.api.enums.TypeOfCarBody;

import java.util.List;
import java.util.Objects;

public class Car extends Vehicle{

    private int numberOfDoors;
    private TypeOfCarBody typeOfCarBody;
    private boolean isManual;

    Car(int id, String name, int yearOfManufacture, double price, double engineSize, int numberOfDoors, TypeOfCarBody typeOfCarBody, boolean isManual, String listedBy) {
        super(id, name, yearOfManufacture, price, engineSize, listedBy);
        this.numberOfDoors = numberOfDoors;
        this.typeOfCarBody = typeOfCarBody;
        this.isManual = isManual;
    }

    static boolean contains(int id, List<Car> items) {
        for (Item item : items) {
            if (item.id == id) {
                return true;
            }
        }
        return false;
    }

    int getNumberOfDoors() {
        return numberOfDoors;
    }

    TypeOfCarBody getTypeOfCarBody() {
        return typeOfCarBody;
    }

//    public boolean isManual() {
//        return isManual;
//    }

    @Override
    public String toString() {
        String gearbox;
        if (isManual) {
            gearbox = "Manual";
        } else {
            gearbox = "Auto";
        }
        return "Car " +
                String.format("%5s", id) +
                " │ " + String.format("%-30s", name) +
                " │ " + String.format("%-13s", typeOfCarBody) +
                " │ " + String.format("%4s", yearOfManufacture) +
                " │ " + String.format("%11s", engineSize + " litres") +
                " │  " + String.format("%2s", numberOfDoors) +
                "   │ " + String.format("%-7s", gearbox) +
                " │ " + String.format("%7s", "€" + price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(
                name, car.name) &&
                yearOfManufacture == car.yearOfManufacture &&
                price == car.price &&
                engineSize == car.engineSize &&
                numberOfDoors == car.numberOfDoors &&
                typeOfCarBody == car.typeOfCarBody &&
                isManual == car.isManual &&
                Objects.equals(listedBy, car.listedBy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, yearOfManufacture, price, engineSize, numberOfDoors, typeOfCarBody, isManual, listedBy);
    }
}
