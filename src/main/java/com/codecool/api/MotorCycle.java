package com.codecool.api;

public class MotorCycle extends Vehicle {

    private TypeOfMotorCycle typeOfMotorCycle;

    public MotorCycle(int id, String name, float price, float engineSize, TypeOfMotorCycle typeOfMotorCycle) {
        super(id, name, price, engineSize);
        this.typeOfMotorCycle = typeOfMotorCycle;
    }

    public MotorCycle() {
    }

    public TypeOfMotorCycle getTypeOfMotorCycle() {
        return typeOfMotorCycle;
    }
}
