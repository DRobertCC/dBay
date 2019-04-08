package com.codecool.api;

public abstract class Item {

    int id;
    String name;
    double price;
    String listedBy; // The username of the lister;


    Item(int id, String name, double price, String listedBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.listedBy = listedBy;
    }

    Item() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }


    public String getListedBy() {
        return listedBy;
    }
}
