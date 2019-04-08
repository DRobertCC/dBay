package com.codecool.api;

import com.codecool.api.enums.TypeOfMotorCycle;

import java.util.List;
import java.util.Objects;

public class MotorCycle extends Vehicle {

    private TypeOfMotorCycle typeOfMotorCycle;

    MotorCycle(int id, String name, int yearOfManufacture, double price, double engineSize, TypeOfMotorCycle typeOfMotorCycle, String listedBy) {
        super(id, name, yearOfManufacture, price, engineSize, listedBy);
        this.typeOfMotorCycle = typeOfMotorCycle;
    }

    public static boolean contains(int id, List<MotorCycle> items) {
        for (Item item : items) {
            if (item.id == id) {
                return true;
            }
        }
        return false;
    }

    public TypeOfMotorCycle getTypeOfMotorCycle() {
        return typeOfMotorCycle;
    }

    @Override
    public String toString() {
        return "MotorCycle " +
                String.format("%5s", id) +
                " │ " + String.format("%-30s", name) +
                " │ " + String.format("%-13s", typeOfMotorCycle) +
                " │ " + String.format("%4s", yearOfManufacture) +
                " │ " + String.format("%10s", engineSize + " litres") +
                " │ " + String.format("%7s", "€" + price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotorCycle motorcycle = (MotorCycle) o;
        return Objects.equals(
                name, motorcycle.name) &&
                yearOfManufacture == motorcycle.yearOfManufacture &&
                price == motorcycle.price &&
                engineSize == motorcycle.engineSize &&
                typeOfMotorCycle == motorcycle.typeOfMotorCycle &&
                Objects.equals(listedBy, motorcycle.listedBy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, yearOfManufacture, price, engineSize, typeOfMotorCycle, listedBy);
    }
}
