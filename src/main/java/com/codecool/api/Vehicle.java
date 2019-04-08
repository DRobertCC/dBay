package com.codecool.api;

abstract class Vehicle extends Item {

    private double engineSize;
    private int yearOfManufacture;

    Vehicle(int id, String name, int yearOfManufacture,  double price, double engineSize, String listedBy) {
        super(id, name, price, listedBy);
        this.yearOfManufacture = yearOfManufacture;
        this.engineSize = engineSize;
    }

    double getEngineSize() {
        return engineSize;
    }

    int getYearOfManufacture() {
        return yearOfManufacture;
    }
}
