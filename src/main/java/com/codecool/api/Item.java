package com.codecool.api;

public abstract class Item {

    protected int id;
    protected String name;
    protected float price;

    public Item(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
