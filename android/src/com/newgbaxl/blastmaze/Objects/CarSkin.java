package com.newgbaxl.blastmaze.Objects;

public class CarSkin {
    //move this to core

    public boolean unlocked;
    public boolean purchased = false;
    public int price;
    //int skinId;


    public CarSkin(int nPrice, boolean nUnlocked){
        price = nPrice;
        unlocked = nUnlocked;

    }
}
