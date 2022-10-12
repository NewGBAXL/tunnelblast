package com.newgbaxl.blastmaze;

public class Coordinates {

    public float X;
    public float Y;

    public Coordinates(float xPosition, float yPosition)
    {
        X = xPosition;
        Y = yPosition;
    }

    public int gridX()
    {
        //Placeholder until I know how the grid is handled
        return (int)(X / Const.TILE_SIZE);
    }

    public int gridY()
    {
        //Placeholder until I know how the grid is handled
        return (int)(Y / Const.TILE_SIZE);
    }
}
