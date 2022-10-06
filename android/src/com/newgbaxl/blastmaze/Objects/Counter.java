package com.newgbaxl.blastmaze.Objects;

import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.Map;

public class Counter extends GameObject{
    int value;
    //some coordinate system to track position of drawable

    Counter(Map game, int x, int y, int value){
        super(game);
        this.value = value;
        position = new Coordinates(x, y);
    }

    public void Update(){
        if (value >= 100)
            value = 99;
        if (value < 0)
            value = 0;
        //toString(value/10)+".png", position.x, position.y;
        //toString(value%10)+".png", position.x, position.y;
    }

    public void setValue(int value){
        this.value = value;
    }
}