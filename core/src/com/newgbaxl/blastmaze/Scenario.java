package com.newgbaxl.blastmaze;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.newgbaxl.blastmaze.objects.EnemyCar;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Scenario
{
    public static Scenario[] scenarios = new Scenario[]
    {
            //Evade
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(10, 6, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 15;
                    scene.user.blocks = 5;
                    scene.user.timer = 60 * 60;

                    scene.coinCount = 2;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (scene.enemies.size() == 0 && MazeScreen2d.coinsRemaining == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {

                    return MazeScreen2d.coinsCollected + 2;
                }
            },

            //Hunt
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(10, 4, Color.RED));
                    scene.enemies.add(new EnemyCar(10, 8, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 20;
                    scene.user.blocks = 10;
                    scene.user.timer = 60 * 60;

                    scene.coinCount = 2;
                    scene.huntEnemyMode = true;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (scene.enemies.size() == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return 0;
                    return (int)MathUtils.clamp(scene.user.timer / 900f + 1, 1, 4);
                }
            },

            //Collect Coins
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(10, 4, Color.RED));
                    scene.enemies.add(new EnemyCar(10, 8, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 10;
                    scene.user.blocks = 10;
                    scene.user.timer = 90 * 60;
                    scene.coinCount = 4;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (MazeScreen2d.coinsRemaining == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {
                    return MazeScreen2d.coinsCollected;
                }
            },

            //Evade
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(10, 4, Color.RED));
                    scene.enemies.add(new EnemyCar(10, 8, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 10;
                    scene.user.blocks = 10;
                    scene.user.timer = 60 * 60;

                    scene.coinCount = 4;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (scene.enemies.size() == 0 && MazeScreen2d.coinsRemaining == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {

                    if (MazeScreen2d.coinsCollected == 0) return 1;
                    if (MazeScreen2d.coinsCollected == 1) return 2;
                    if (MazeScreen2d.coinsCollected == 2) return 3;
                    if (MazeScreen2d.coinsCollected == 3) return 3;
                    if (MazeScreen2d.coinsCollected == 4) return 4;
                    return 0;
                }
            },

            //Bomb enemies
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(9, 3, Color.RED));
                    scene.enemies.add(new EnemyCar(12, 6, Color.RED));
                    scene.enemies.add(new EnemyCar(9, 9, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 20;
                    scene.user.blocks = 20;
                    scene.user.timer = 120 * 60;

                    scene.coinCount = 3;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (scene.enemies.size() == 0 && MazeScreen2d.coinsRemaining == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {

                    if (scene.enemies.size() == 3) return 0;
                    if (scene.enemies.size() == 2) return 2;
                    if (scene.enemies.size() == 1) return 3;
                    if (scene.enemies.size() == 0) return 4;
                    return 0;
                }
            },

            //Hunt
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(9, 3, Color.RED));
                    scene.enemies.add(new EnemyCar(12, 6, Color.RED));
                    scene.enemies.add(new EnemyCar(9, 9, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 15;
                    scene.user.blocks = 10;
                    scene.user.timer = 90 * 60;

                    scene.coinCount = 4;
                    scene.huntEnemyMode = true;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (scene.enemies.size() == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return 0;
                    return (int)MathUtils.clamp(scene.user.timer / 1350 + 1, 1, 4);
                }
            },

            //Evade
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(9, 3, Color.RED));
                    scene.enemies.add(new EnemyCar(12, 6, Color.RED));
                    scene.enemies.add(new EnemyCar(9, 9, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 10;
                    scene.user.blocks = 5;
                    scene.user.timer = 120 * 60;

                    scene.coinCount = 4;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (scene.enemies.size() == 0 && MazeScreen2d.coinsRemaining == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {

                    if (MazeScreen2d.coinsCollected == 0) return 1;
                    if (MazeScreen2d.coinsCollected == 1) return 2;
                    if (MazeScreen2d.coinsCollected == 2) return 3;
                    if (MazeScreen2d.coinsCollected == 3) return 3;
                    if (MazeScreen2d.coinsCollected == 4) return 4;
                    return 0;
                }
            },

            //Hunt
            new Scenario() {
                @Override
                public void OnStart(MazeScreen2d scene) {
                    scene.enemies.add(new EnemyCar(9, 3, Color.RED));
                    scene.enemies.add(new EnemyCar(12, 6, Color.RED));
                    scene.enemies.add(new EnemyCar(9, 9, Color.RED));
                    for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

                    scene.user.bombs = 10;
                    scene.user.blocks = 10;
                    scene.user.timer = 60 * 60;

                    scene.coinCount = 4;
                    scene.huntEnemyMode = true;
                }

                @Override
                public void Update(MazeScreen2d scene) {

                }

                @Override
                public boolean CheckForWin(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return true;
                    if (scene.enemies.size() == 0) return true;
                    return false;
                }

                @Override
                public int getRankResult(MazeScreen2d scene) {
                    if (scene.user.timer <= 0) return 0;
                    return (int)MathUtils.clamp(scene.user.timer / 900 + 1, 1, 4);
                }
            },

        //Example scenario, feel free to create more in this array
        new Scenario() {
        @Override
        public void OnStart(MazeScreen2d scene) {
            scene.enemies.add(new EnemyCar(10, 4, Color.RED));
            scene.enemies.add(new EnemyCar(10, 8, Color.RED));
            for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

            scene.user.timer = 300 * 60;
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

        @Override
        public int getRankResult(MazeScreen2d scene){
            if (!CheckForWin(scene))
                return -1; //still playing
            else
                return 4;
        }
    }
};

    //Run when the game starts. Use for creating enemies, objects, etc.
    public abstract void OnStart(MazeScreen2d scene);
    //Run every frame during the main update
    public abstract void Update(MazeScreen2d scene);
    //Customizable condition for when you should win the game. i.e. return true when you have enough points or there are no more enemies or something
    public abstract boolean CheckForWin(MazeScreen2d scene);

    public abstract int getRankResult(MazeScreen2d scene);
}
