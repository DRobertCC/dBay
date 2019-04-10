package com.codecool.api;

import com.codecool.api.enums.TypeOfMotorCycle;

import java.util.Objects;

public class MotorCycle extends Vehicle {

    private TypeOfMotorCycle typeOfMotorCycle;

    public MotorCycle(int id, String name, int yearOfManufacture, double price, double engineSize, TypeOfMotorCycle typeOfMotorCycle, String listedBy) {
        super(id, name, yearOfManufacture, price, engineSize, listedBy);
        this.typeOfMotorCycle = typeOfMotorCycle;
    }

    public TypeOfMotorCycle getTypeOfMotorCycle() {
        return typeOfMotorCycle;
    }

    @Override
    public String toString() {
        return String.format("%6s", this.getId()) +
                " │ " + String.format("%-30s", this.getName()) +
                " │ " + String.format("%-13s", typeOfMotorCycle) +
                " │ " + String.format("%4s", this.getYearOfManufacture()) +
                " │ " + String.format("%11s", this.getEngineSize() + " litres") +
                " │ " + String.format("%7s", "€" + this.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotorCycle motorcycle = (MotorCycle) o;
        return Objects.equals(
                this.getName(), motorcycle.getName()) &&
                this.getYearOfManufacture() == motorcycle.getYearOfManufacture() &&
                this.getPrice() == motorcycle.getPrice() &&
                this.getEngineSize() == motorcycle.getEngineSize() &&
                typeOfMotorCycle == motorcycle.getTypeOfMotorCycle() &&
                Objects.equals(this.getListedBy(), motorcycle.getListedBy());
    }

    @Override
    public int hashCode() {

        return Objects.hash( this.getName(), this.getYearOfManufacture(), this.getPrice(), this.getEngineSize(), this.getListedBy() );
    }
}
