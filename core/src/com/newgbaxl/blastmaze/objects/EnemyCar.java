package com.newgbaxl.blastmaze.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.newgbaxl.blastmaze.Const;
import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.MazeUtil;

import java.util.Random;

public class EnemyCar extends Car {
    int xPos = 0;
    int yPos = 0;

    public int rp = 0;
    public int lp = 0;
    public int up = 0;
    public int dp = 0;

    int moveSpd = 10;

    final int MoveCooldown = 4;
    int moveCooldownTimer;
    static Random rand = new Random();

    //public boolean moving = false;
    public EnemyCar(int width, int height, Color nSkin, float delay, byte nBaseSpd, byte nPwrRate, int nMoveSpd) {
        super(width, height, nSkin, delay, nBaseSpd, nPwrRate);
        nSkin = Color.GREEN;
        position = new Coordinates(10 * Const.TILE_SIZE, 6 * Const.TILE_SIZE);
        position.gridX = 10;
        position.gridY = 6;
    }

    public void act(float delta) {
        super.act(delta);

        //if (Gdx.graphics.getDeltaTime() > 1)
        //    calculateAction();

        moveCooldownTimer--;
        if(moveCooldownTimer <= 0) {
            calculateAction();
            moveCooldownTimer = (int)((rand.nextInt(40) + 20) * game.enemySpeedMultiplier);
            Gdx.app.log("tag", "Move NPC");
        }

        if (position.gridX == MazeUtil.getPlayerPosition().gridX && position.gridY == MazeUtil.getPlayerPosition().gridY)
            game.quitGame();
    }

    public byte calculateAction(){
        //for all getPathPriority
        //try paths in order of path priority
        byte[] p = getPathPriority();
        /*int maxDir = 0;
        for (int i = 1; i < 4; ++i)
        {
            if (p[i] > p[maxDir]){
                maxDir = i;
            }
        }*/

        //hardcoded version lol
        if (up >= rp && up >= dp && up >= lp)
        {
            if (up < 0)
                destroyAll(1);
            moveTo((byte)0);
        }

        else if (rp >= up && rp >= dp && rp >= lp)
        {
            if (rp < 0)
                destroyAll(1);
            moveTo((byte)1);
        }
        else if (dp >= up && dp >= rp && dp >= lp)
        {
            if (dp < 0)
                destroyAll(1);
            moveTo((byte)2);
        }
        else if (lp >= up && lp >= rp && lp >= dp)
        {
            if (lp < 0)
                destroyAll(1);
            moveTo((byte)3);
        }

        //if path p is negative && != -3, use bomb, then move
        /*if (p[maxDir] < 0)
            destroyAll(1);

        moveTo(p[maxDir]);*/

        //other notes:
        //try closest Y
        //try closest X
        //try other route not taken
        //try bomb
        //try return route

        return 0;
    }

    //delete?
    public byte closestPath()
    {
        if (MazeUtil.getPlayerPosition().gridY < position.gridY && isValidMove((byte)0))
            return 0;
        else if (MazeUtil.getPlayerPosition().gridX > position.gridX && isValidMove((byte)1))
            return 1;
        else if (MazeUtil.getPlayerPosition().gridY > position.gridY && isValidMove((byte)2))
            return 2;
        else
            return 3;
    }

    public byte[] getPathPriority()
    {
        //messy algorithm :)
        byte priorities[] = new byte[4];

        for (byte i = 0; i < 4; ++i) //for all directions
        {
            if ((i == 0 && MazeUtil.getPlayerPosition().gridY > position.gridY) ||
                    (i == 1 && MazeUtil.getPlayerPosition().gridX > position.gridX) ||
                    (i == 2 && MazeUtil.getPlayerPosition().gridY < position.gridY) ||
                    (i == 3 && MazeUtil.getPlayerPosition().gridX < position.gridX))
            {
                if (i != lastPos)
                {
                    if (isValidMove(i))
                        priorities[i] = 4; //closest to player, valid move
                    else
                        priorities[i] = -1; //closest to player, invalid move
                }
                else
                    priorities[i] = -3; //closest to player, but came from there
            }

            else if (isValidMove(i))
                if (i != lastPos)
                    priorities[i] = 2; //valid move
                else
                    priorities[i] = 1; //valid move but turning around
            else
                priorities[i] = -2; //invalid move
        }

        up = priorities[0];
        rp = priorities[1];
        dp = priorities[2];
        lp = priorities[3];

        return priorities;
    }
}
