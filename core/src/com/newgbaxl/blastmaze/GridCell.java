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

    //int cellSpd=10; //planned feature; works with "oilSlick"

    public GridCell(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}