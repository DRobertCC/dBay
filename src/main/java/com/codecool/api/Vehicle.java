package com.codecool.api;

public abstract class Vehicle extends Item {

    protected float engineSize;

    public Vehicle(int id, String name, float price, float engineSize) {
        super(id, name, price);
        this.engineSize = engineSize;
    }

    public Vehicle() {
    }

    public float getEngineSize() {
        return engineSize;
    }
}
