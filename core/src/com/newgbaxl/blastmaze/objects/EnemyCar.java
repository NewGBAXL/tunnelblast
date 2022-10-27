package com.newgbaxl.blastmaze.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.newgbaxl.blastmaze.Const;
import com.newgbaxl.blastmaze.Coordinates;

public class EnemyCar extends Car {
    int xPos = 0;
    int yPos = 0;

    int moveSpd = 10;

    final int MoveCooldown = 4;
    int moveCooldownTimer;

    //public boolean moving = false;
    public EnemyCar(int width, int height, Color nSkin, float delay, byte nBaseSpd, byte nPwrRate, int nMoveSpd) {
        super(width, height, nSkin, delay, nBaseSpd, nPwrRate);
        nSkin = Color.GREEN;
        position = new Coordinates(Const.SPAWN_CELL_X * Const.TILE_SIZE, Const.SPAWN_CELL_Y * Const.TILE_SIZE);
        position.gridX = Const.SPAWN_CELL_X;
        position.gridY = Const.SPAWN_CELL_Y;
    }

    public void act(float delta) {
        super.act(delta);

        if (1 % moveCooldownTimer == 0)
            calculateAction();
    }

    public byte calculateMovePos(){

        return (byte)0;
    }

    public byte calculateAction(){
        //if player is to the north and open space to the north
        //return 0
        //..

        //try 90deg move

        //try break wall towards player

        return (byte)0;
    }



}
