package com.newgbaxl.blastmaze.Objects;

import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.Map;
import com.newgbaxl.blastmaze.Objects.GameObject;
import com.newgbaxl.blastmaze.databinding.ActivityUsercarBinding;

public class Car extends GameObject
{
    private AppBarConfiguration appBarConfiguration;
    private ActivityUsercarBinding binding;
    public int bombs = 50;
    public int blocks = 50;
    private Paint skin;
    byte baseSpd; //planned feature (oil slick)
    byte pwrRate; //pwr regeneration rate
    byte spd;
    public int power;
    public int timer;
    byte lastPos = 0;

    public Car(Map game, int x, int y, Paint nSkin, byte nBaseSpd, byte nPwrRate)
    {
        super(game);
        power = 10;
        timer = 10;
        skin = nSkin;
        position = new Coordinates(x, y);
        baseSpd = nBaseSpd;
        pwrRate = nPwrRate;
        bombs = 10;
        //color = ?? //is color separate from skin?
    }

    public boolean destroy(byte cardinal, int str)
    {
        //boolean returnBool = Map.cells[position.gridX()][position.gridY()].breakWall(cardinal, str);
        --bombs;
        return false;
    }

    public boolean build(byte cardinal)
    {
        if (!isValidMove(cardinal))
            return false;

        //boolean returnBool = Map.cells[position.gridX()][position.gridY()].buildWall(cardinal);
        --blocks;
        return false;
    }

    public boolean isValidMove(byte dir)
    {
        if (lastPos == dir)
            return false;
        if (dir==0 && Map.cells[position.gridX()][position.gridY()].nWall == 0)
            return true;
        if (dir==1 && Map.cells[position.gridX()][position.gridY()].eWall == 0)
            return true;
        if (dir==2 && Map.cells[position.gridX()][position.gridY()].sWall == 0)
            return true;
        if (dir==3 && Map.cells[position.gridX()][position.gridY()].wWall == 0)
            return true;
        return false;
    }

    public void moveTo(byte newDir){
        //check all routes clockwise for valid move
        for (int counter = 0; !isValidMove(newDir) && counter < 4; ++counter){
            newDir = (byte)((newDir+1)%4); //cycles 0-3
        }

        //if is still invalid turn around or game over?
        //(if counter >=4)
        //Map.loseUI()?

        if (newDir == 0)
            --position.Y;
        else if (newDir == 1)
            ++position.X;
        else if (newDir == 2)
            ++position.Y;
        else if (newDir == 3)
            --position.X;
        lastPos = (byte)((newDir+2)%4); //update lastPos, cycles 0-3
        Map.inv = true;
        return;
    }

    //planned feature
    /*public void oilSlick(int intensity){
        Map.cells[xPos][yPos].setSpd(intensity);
        return;
    }*/
}