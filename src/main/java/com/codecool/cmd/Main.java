package com.codecool.cmd;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        Menu menu = new Menu();

        while (true) {
            menu.handleMenu();
            Thread.sleep(100);
            menu.choose();
        }
    }

}
