package com.codecool.api;

public class Car extends Vehicle{

    private int numberOfDoors;
    private TypeOfCarBody typeOfCarBody;
    private boolean isManual;

    public Car(int id, String name, float price, float engineSize, int numberOfDoors, TypeOfCarBody typeOfCarBody, boolean isManual) {
        super(id, name, price, engineSize);
        this.numberOfDoors = numberOfDoors;
        this.typeOfCarBody = typeOfCarBody;
        this.isManual = isManual;
    }

    public Car() {
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public TypeOfCarBody getTypeOfCarBody() {
        return typeOfCarBody;
    }

    public boolean isManual() {
        return isManual;
    }

    @Override
    public String toString() {
        return "Car{" +
                "numberOfDoors=" + numberOfDoors +
                ", typeOfCarBody=" + typeOfCarBody +
                ", isManual=" + isManual +
                ", engineSize=" + engineSize +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
