package com.newgbaxl.blastmaze.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.newgbaxl.blastmaze.Const;
import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.HeadsUpDisplay;

public class UserCar extends Car {
    int xPos = 0;
    int yPos = 0;

    final int MoveCooldown = 4;
    int moveCooldownTimer;

    //public boolean moving = false;
    public UserCar(int width, int height, Color nSkin, float delay, byte nBaseSpd, byte nPwrRate)
    {
        super(width, height, nSkin, delay, nBaseSpd, nPwrRate);
        nSkin = Color.GREEN;
        position = new Coordinates(Const.SPAWN_CELL_X * Const.TILE_SIZE, Const.SPAWN_CELL_Y * Const.TILE_SIZE);
        position.gridX = Const.SPAWN_CELL_X;
        position.gridY = Const.SPAWN_CELL_Y;
    }

    //@Override
    public boolean keyUp(InputEvent event, int keycode)
    {
        moveState = PlayerMoveState.IDLE;
        return true;
    }

    /*@Override
    public void create() {
        Gdx.input.setInputProcessor(this);
    }*/

    @Override
    public void act(float delta)
    {
        super.act(delta);
        if (timer > 0) timer--;
        //switch(moveState) {
        //    case RIGHT :
//
        //        // Move right by using whatever method you want.
        //        // Directly increasing x
        //        // Increase x according to velocity you have defined.
        //        // Use actions, little bit dangerous.
        //        break;
        //    case LEFT :
        //        // Move left by using whatever method you want.
        //        break;
        //    case IDLE :
        //        // Don't change x coordinate of your player.
        //        break;
        //    default :
//
        //        break;
        //}

        if(Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && moveCooldownTimer <= 0) {
            //if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            moveTo((byte)3);
            Gdx.app.log("tag", "Left");
            moveCooldownTimer = MoveCooldown;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && moveCooldownTimer <= 0) {
            //if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            moveTo((byte)1);
            Gdx.app.log("tag", "Right");
            moveCooldownTimer = MoveCooldown;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP) && moveCooldownTimer <= 0) {
            //if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            moveTo((byte)0);
            Gdx.app.log("tag", "Up");
            moveCooldownTimer = MoveCooldown;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && moveCooldownTimer <= 0) {
            //if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            moveTo((byte)2);
            Gdx.app.log("tag", "Down");
            moveCooldownTimer = MoveCooldown;
        }

        if((Gdx.input.isKeyJustPressed(Input.Keys.BUTTON_X) || Gdx.input.isKeyJustPressed(Input.Keys.X)) && moveCooldownTimer <= 0) {

            //Kill adjacent enemies
            if (bombs > 0) {
                for (Object e : game.enemies.toArray()) {
                    if (e.getClass() == EnemyCar.class) {
                        Coordinates pos = ((EnemyCar) e).position;
                        if ((pos.gridY == position.gridY && pos.gridX == position.gridX) ||
                                (pos.gridY == position.gridY && pos.gridX == position.gridX + 1) ||
                                (pos.gridY == position.gridY && pos.gridX == position.gridX - 1) ||
                                (pos.gridY == position.gridY + 1 && pos.gridX == position.gridX) ||
                                (pos.gridY == position.gridY - 1 && pos.gridX == position.gridX)) {
                            game.enemies.remove(e);
                            game.stage.getActors().removeValue((Actor) e, true);
                        }
                    }
                }
            }
            destroyAll(1);

            Gdx.app.log("tag", "Button X");
            moveCooldownTimer = MoveCooldown;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.BUTTON_Z) || Gdx.input.isKeyJustPressed(Input.Keys.I) && moveCooldownTimer <= 0) {
            build((byte)0);
            Gdx.app.log("tag", "Button X");
            moveCooldownTimer = MoveCooldown;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.L) && moveCooldownTimer <= 0) {
            build((byte)1);
            Gdx.app.log("tag", "Button X");
            moveCooldownTimer = MoveCooldown;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.K) && moveCooldownTimer <= 0) {
            build((byte)2);
            Gdx.app.log("tag", "Button X");
            moveCooldownTimer = MoveCooldown;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.J) && moveCooldownTimer <= 0) {
            build((byte)3);
            Gdx.app.log("tag", "Button X");
            moveCooldownTimer = MoveCooldown;
        }

        if (moveCooldownTimer > 0) moveCooldownTimer--;
    }

    /*@Override
    public void Update() {
        //add timeout, this should fix build/break

        //move
        if (game.UIBinding.upButton.isPressed()) moveTo((byte) 0);
        if (game.UIBinding.downButton.isPressed()) moveTo((byte) 2);
        if (game.UIBinding.leftButton.isPressed()) moveTo((byte) 3);
        if (game.UIBinding.rightButton.isPressed()) moveTo((byte) 1);


        if (game.UIBinding.buildButton.isPressed()) build((byte)0);
        //destroy
        if (game.UIBinding.upButton.isPressed() && game.UIBinding.breakButton.isPressed()) destroy((byte) 0, 1);
        if (game.UIBinding.downButton.isPressed() && game.UIBinding.breakButton.isPressed()) destroy((byte) 2, 1);
        if (game.UIBinding.leftButton.isPressed() && game.UIBinding.breakButton.isPressed()) destroy((byte) 3, 1);
        if (game.UIBinding.rightButton.isPressed() && game.UIBinding.breakButton.isPressed()) destroy((byte) 1, 1);

        //build
        if (game.UIBinding.upButton.isPressed() && game.UIBinding.buildButton.isPressed()) build((byte) 0);
        if (game.UIBinding.downButton.isPressed() && game.UIBinding.buildButton.isPressed()) build((byte) 2);
        if (game.UIBinding.leftButton.isPressed() && game.UIBinding.buildButton.isPressed()) build((byte) 3);
        if (game.UIBinding.rightButton.isPressed() && game.UIBinding.buildButton.isPressed()) build((byte) 1);

        //evade
        //if (game.UIBinding.evadeButton.isPressed() oilSlick(1);
        //planned feature

        super.Update();
    }*/

    //public boolean destroy(byte cardinal, int str)
    //{
    //    boolean returnBool = super.destroy(cardinal, str);
    //    HeadsUpDisplay.bombs = bombs;
    //    return returnBool;
    //}
//
    //public boolean build(byte cardinal)
    //{
    //    boolean returnBool = super.build(cardinal);
    //    HeadsUpDisplay.blocks = blocks;
    //    return returnBool;
    //}

    //use power
    //timer
    //..
}


