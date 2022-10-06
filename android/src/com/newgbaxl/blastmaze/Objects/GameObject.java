package com.newgbaxl.blastmaze.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.Map;
import com.newgbaxl.blastmaze.R;

import java.io.IOException;
import java.io.InputStream;

public class GameObject
{
    protected Map game;

    public Coordinates position;
    public Drawable sprite;

    public GameObject(Map game)
    {
        this.game = game;
        position = new Coordinates(0, 0);

        sprite = game.getResources().getDrawable(R.drawable.car, null);
    }

    public void Update(){}

    public void Draw(Canvas canvas)
    {
        //sprite.setBounds((int)(position.X * game.cellSize), (int)(position.Y * game.cellSize),
        //        (int)((position.X + 1) * game.cellSize),(int)((position.Y+1) * game.cellSize));
        sprite.setBounds((int)position.X - sprite.getMinimumWidth() / 2, (int)position.Y - sprite.getMinimumHeight() / 2, (int)position.X + sprite.getMinimumWidth() / 2, (int)position.Y + sprite.getMinimumHeight() / 2);
        sprite.draw(canvas);
    }
}
