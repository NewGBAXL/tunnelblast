package com.newgbaxl.blastmaze.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.Maze;
import com.newgbaxl.blastmaze.MazeScreen2d;

import java.io.IOException;
import java.io.InputStream;

public class CarActorAbs extends Actor
{
    protected MazeScreen2d game;
    protected Color color;

    public Coordinates position;
    //public Drawable sprite;
    public Texture sprite;

    public CarActorAbs(Color color) {
        super();
        this.setWidth(32);
        this.setHeight(32);
        this.setVisible(true);
        this.setPosition(0, 0);
        this.color = color;
        init();
        game = MazeScreen2d.getInstance;
    }

    public void init() {};

    public void Update(){}

    private ShapeRenderer renderer = new ShapeRenderer();

    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(position.X, position.Y, 0);

        renderer.begin(ShapeType.Filled);
        renderer.setColor(color);
        renderer.ellipse(0, 0, getWidth(), getHeight());
        renderer.end();

        batch.begin();

        //batch.begin();
        //batch.draw(sprite, position.X, position.Y);
        //batch.end();
    }
}
