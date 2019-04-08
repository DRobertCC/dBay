package com.codecool.api;

public abstract class Vehicle extends Item {

    protected double engineSize;
    protected int yearOfManufacture;

    public Vehicle(int id, String name, int yearOfManufacture,  double price, double engineSize, String listedBy) {
        super(id, name, price, listedBy);
        this.yearOfManufacture = yearOfManufacture;
        this.engineSize = engineSize;
    }

    public Vehicle() {
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public double getEngineSize() {
        return engineSize;
    }
}
