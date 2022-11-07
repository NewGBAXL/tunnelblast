package com.newgbaxl.blastmaze.Objects;

public class CarSkin
{
    //move this to core
    private int id;
    public boolean unlocked;
    public boolean purchaced;
    public int price;
    //int skinId;


    public CarSkin(int nPrice, boolean nUnlocked)
    {
        price = nPrice;
        unlocked = nUnlocked;
        this.purchaced = false;

    }

    public CarSkin(boolean unlocked, boolean purchaced, int price)
    {
        this.unlocked = unlocked;
        this.purchaced = purchaced;
        this.price = price;
    }

    public CarSkin(int id, boolean unlocked, boolean purchaced, int price)
    {
        this.id = id;
        this.unlocked = unlocked;
        this.purchaced = purchaced;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
