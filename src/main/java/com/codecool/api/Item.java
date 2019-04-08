package com.codecool.api;

abstract class Item {

    private int id;
    private String name;
    private double price;
    private String listedBy; // The username of the lister;


    Item(int id, String name, double price, String listedBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.listedBy = listedBy;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }


    String getListedBy() {
        return listedBy;
    }
}
