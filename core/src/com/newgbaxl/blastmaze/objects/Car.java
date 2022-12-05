package com.newgbaxl.blastmaze.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.newgbaxl.blastmaze.Const;
import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.Maze;
import com.newgbaxl.blastmaze.MazeScreen2d;
import com.newgbaxl.blastmaze.MazeUtil;

public class Car extends CarActorAbs
{
    //private AppBarConfiguration appBarConfiguration;
    //private ActivityUsercarBinding binding;
    public int bombs = 50;
    public int blocks = 50;
    private Color skin;
    byte baseSpd; //planned feature (oil slick)
    byte pwrRate; //pwr regeneration rate
    byte spd;
    public int power;
    public float timer;
    byte lastPos = 0;

    public enum PlayerMoveState {
        RIGHT,
        LEFT,
        IDLE
    }
    PlayerMoveState moveState;

    public Car(int width, int height, Color nSkin, float delay, byte nBaseSpd, byte nPwrRate)
    {
        super(width, height, nSkin, delay);
        power = 10;
        timer = 10;
        skin = nSkin;
        //position = new Coordinates(x, y);
        baseSpd = nBaseSpd;
        pwrRate = nPwrRate;
        bombs = 10;
        //sprite = new Texture("car");
        //color = ?? //is color separate from skin?
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position.X = MathUtils.lerp(position.X, position.gridX * Const.TILE_SIZE, 0.25f);
        position.Y = MathUtils.lerp(position.Y, position.gridY * Const.TILE_SIZE, 0.25f);
    }

    public boolean destroy(byte cardinal, int str)
    {
        if (bombs <= 0)
        {
            bombs = 0;
            return false;
        }

        if (str > 0)
            --bombs;
        str = Math.abs(str);

        int currentStr = MazeUtil.GetWallStrength(position.gridX, position.gridY, cardinal);
        if (currentStr <= 0)
            return false;
        else if (str > currentStr)
            MazeUtil.SetWallStrength(position.gridX, position.gridY, cardinal, 0);
        else
            MazeUtil.SetWallStrength(position.gridX, position.gridY, cardinal, currentStr - str);


        return true;
    }

    public boolean destroyAll(int str)
    {
        if (bombs <= 0){
            bombs = 0;
            return false;
        }

        boolean returnBool = false;

        for (int i = 0; i <= 3; ++i)
            if (destroy((byte)i, -str))
                returnBool = true;

        if (returnBool)
            --bombs;
        return returnBool;
    }

    public boolean build(byte cardinal)
    {
        if (!isValidMove(cardinal) || blocks <= 0)
            return false;

        MazeUtil.SetWallStrength(position.gridX, position.gridY, cardinal, 1);
        --blocks;
        return true;
    }

    public boolean isValidMove(byte dir)
    {
        //if (lastPos == dir)
        //    return false;

        //Check in bounds
        if (position.gridX < 0 || position.gridX > Const.MAZE_WIDTH - 1 || position.gridY < 0 || position.gridY > Const.MAZE_HEIGHT - 1) return true;

        //Check current tile sides
        if (MazeUtil.GetWallStrength(position.gridX, position.gridY, dir) != 0) return false;

        return true;
    }

    public void moveTo(byte newDir){
        //check all routes clockwise for valid move
        //for (int counter = 0; !isValidMove(newDir) && counter < 4; ++counter){
        //    newDir = (byte)((newDir+1)%4); //cycles 0-3
        //}
        if (!isValidMove(newDir))
        {
            if (newDir == 0)
                position.Y = position.gridY * Const.TILE_SIZE + 16;
            else if (newDir == 1)
                position.X = position.gridX * Const.TILE_SIZE + 16;
            else if (newDir == 2)
                position.Y = position.gridY * Const.TILE_SIZE - 16;
            else if (newDir == 3)
                position.X = position.gridX * Const.TILE_SIZE - 16;

            return;
        }

        //if is still invalid turn around or game over?
        //(if counter >=4)
        //Map.loseUI()?

        lastPos = (byte)((newDir+2)%4); //update lastPos, cycles 0-3
        if (newDir == 0)
            position.gridY++;
        else if (newDir == 1)
            position.gridX++;
        else if (newDir == 2)
            position.gridY--;
        else if (newDir == 3)
            position.gridX--;

        switch (MazeUtil.GetCellData(position.gridX,position.gridY))
        {
            case -2:
                ++blocks;
                break;
            case -3:
                ++bombs;
                break;
            case -4:
                ++power;
                break;
            case -5:
                ++timer;
                break;
            case -6:
                if (game.user == this) MazeScreen2d.coinsCollected++;
                MazeScreen2d.coinsRemaining--;
                break;
            default:
                //oilSlick(MazeUtil.GetCellData(position.gridX,position.gridY));
                //use intensity to change speed in cell
                break;
        }

        MazeUtil.SetCellData(position.gridX,position.gridY, (byte)0);
        return;
    }

    //planned feature
    /*public void oilSlick(int intensity){
        Map.cells[xPos][yPos].setSpd(intensity);
        return;
    }*/
}