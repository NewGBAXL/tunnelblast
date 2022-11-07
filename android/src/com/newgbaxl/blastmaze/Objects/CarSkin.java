package com.newgbaxl.blastmaze.Objects;

public class CarSkin {
    //move this to core
    private int id;
    public boolean unlocked;
    public boolean purchased;
    public int price;
    //int skinId;


    public CarSkin(int nPrice, boolean nUnlocked){
        price = nPrice;
        unlocked = nUnlocked;
        this.purchased = false;

    }

    public CarSkin(boolean unlocked, boolean purchased, int price) {
        this.unlocked = unlocked;
        this.purchased = purchased;
        this.price = price;
    }

    public CarSkin(int id, boolean unlocked, boolean purchased, int price) {
        this.id = id;
        this.unlocked = unlocked;
        this.purchased = purchased;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
