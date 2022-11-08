package com.newgbaxl.blastmaze;

import com.badlogic.gdx.graphics.Color;
import com.newgbaxl.blastmaze.objects.EnemyCar;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Scenario
{
    public static Scenario[] scenarios = new Scenario[]
    {
        //Example scenario, feel free to create more in this array
        new Scenario() {
        @Override
        public void OnStart(MazeScreen2d scene) {
            //Add 2 enemy cars to the scene
            scene.enemies = new LinkedList<>();
            for (int i = 1; i < 2; ++i)
            {
                scene.enemies.add(new EnemyCar(32,32, Color.BLACK,
                    (float)(Math.random() * 0.6) + 0.1f, (byte)1, (byte)1,1));
                    scene.stage.addActor(scene.enemies.peekLast());
            }
        }

        @Override
        public void Update(MazeScreen2d scene) {
            //Nothing in particular to update
        }

        @Override
        public boolean CheckForWin(MazeScreen2d scene) {
            //No win condition
            return false;
        }
    }
};

    //Run when the game starts. Use for creating enemies, objects, etc.
    public abstract void OnStart(MazeScreen2d scene);
    //Run every frame during the main update
    public abstract void Update(MazeScreen2d scene);
    //Customizable condition for when you should win the game. i.e. return true when you have enough points or there are no more enemies or something
    public abstract boolean CheckForWin(MazeScreen2d scene);
}
