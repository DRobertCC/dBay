package com.codecool.api;

import com.codecool.api.enums.TypeOfCarBody;

import java.util.List;
import java.util.Objects;

public class Car extends Vehicle{

    private int numberOfDoors;
    private TypeOfCarBody typeOfCarBody;
    private boolean isManual;

    public Car(int id, String name, int yearOfManufacture, double price, double engineSize, int numberOfDoors, TypeOfCarBody typeOfCarBody, boolean isManual, String listedBy) {
        super(id, name, yearOfManufacture, price, engineSize, listedBy);
        this.numberOfDoors = numberOfDoors;
        this.typeOfCarBody = typeOfCarBody;
        this.isManual = isManual;
    }

    static boolean contains(int id, List<Car> items) {
        for (Item item : items) {
            if (item.getId() == id) {
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

    private boolean getIsManual() {
        return isManual;
    }

    @Override
    public String toString() {
        String gearbox;
        if (isManual) {
            gearbox = "Manual";
        } else {
            gearbox = "Auto";
        }
        return "Car " +
                String.format("%5s", this.getId()) +
                " │ " + String.format("%-30s", this.getName()) +
                " │ " + String.format("%-13s", typeOfCarBody) +
                " │ " + String.format("%4s", this.getYearOfManufacture()) +
                " │ " + String.format("%11s", this.getEngineSize() + " litres") +
                " │  " + String.format("%2s", numberOfDoors) +
                "   │ " + String.format("%-7s", gearbox) +
                " │ " + String.format("%7s", "€" + this.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(
                this.getName(), car.getName()) &&
                this.getYearOfManufacture() == car.getYearOfManufacture() &&
                this.getPrice() == car.getPrice() &&
                this.getEngineSize() == car.getEngineSize() &&
                numberOfDoors == car.getNumberOfDoors() &&
                typeOfCarBody == car.typeOfCarBody &&
                isManual == car.getIsManual() &&
                Objects.equals(this.getListedBy(), car.getListedBy());
    }

    @Override
    public int hashCode() {

        return Objects.hash( this.getName(), this.getYearOfManufacture(), this.getPrice(), this.getEngineSize(), this.getNumberOfDoors(), typeOfCarBody, isManual, this.getListedBy() );
    }
}
