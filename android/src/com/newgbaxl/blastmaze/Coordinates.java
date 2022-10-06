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
        return (int)(X / Map.cellSize);
    }

    public int gridY()
    {
        //Placeholder until I know how the grid is handled
        return (int)(Y / Map.cellSize);
    }
}
