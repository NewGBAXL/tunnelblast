package com.newgbaxl.blastmaze.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.Maze;

import java.io.IOException;
import java.io.InputStream;

public class GameObject
{
    protected Maze game;

    public Coordinates position;
    //public Drawable sprite;
    public Texture sprite;

    public GameObject(Maze game)
    {
        this.game = game;
        position = new Coordinates(0, 0);
    }

    public void Update(){}

    /*public void Draw(Canvas canvas)
    {
        //sprite.setBounds((int)(position.X * game.cellSize), (int)(position.Y * game.cellSize),
        //        (int)((position.X + 1) * game.cellSize),(int)((position.Y+1) * game.cellSize));
        //sprite.setBounds((int)position.X - sprite.getMinimumWidth() / 2, (int)position.Y - sprite.getMinimumHeight() / 2, (int)position.X + sprite.getMinimumWidth() / 2, (int)position.Y + sprite.getMinimumHeight() / 2);
        //sprite.draw(canvas);
    }*/
}
