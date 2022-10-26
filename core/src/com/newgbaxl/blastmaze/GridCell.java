package com.newgbaxl.blastmaze;

import com.badlogic.gdx.graphics.Color;

public class GridCell {
    public byte nWall = 0;
    public byte eWall = 0;
    public byte sWall = 0;
    public byte wWall = 0;
    Color wallColor;

    public int x;
    public int y;

    public byte cellData = -1;
    //-1 is empty
    //+n is cellSpd
    //-2 is block
    //-3 is bomb
    //-4 is power
    //-5 is clock (extra time)

    public GridCell(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}