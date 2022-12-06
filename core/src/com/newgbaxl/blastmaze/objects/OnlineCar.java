package com.newgbaxl.blastmaze.objects;

import static com.newgbaxl.blastmaze.OnlineAdapter.AppKey;
import static com.newgbaxl.blastmaze.OnlineAdapter.SecretKey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.newgbaxl.blastmaze.Const;
import com.newgbaxl.blastmaze.Coordinates;
import com.newgbaxl.blastmaze.appwarp.WarpController;
import com.newgbaxl.blastmaze.appwarp.WarpListener;
import com.newgbaxl.blastmaze.multiplayer.StreamController;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

public class OnlineCar extends Car implements WarpListener {
    //experimental

    //StreamController will receive user input and send it as JSON data to the other phone
    //OnlineCar will receive JSON data from the other phone and attempt to use that action

    int xPos = 0;
    int yPos = 0;

    final int MoveCooldown = 4;
    int moveCooldownTimer;

    WarpController appwarp;

    public OnlineCar(int x, int y, Color nSkin)
    {
        super(x, y, nSkin);
        nSkin = Color.GREEN;
        position = new Coordinates(Const.SPAWN_CELL_X * Const.TILE_SIZE, Const.SPAWN_CELL_Y * Const.TILE_SIZE);
        position.gridX = Const.SPAWN_CELL_X;
        position.gridY = Const.SPAWN_CELL_Y;

        appwarp = new WarpController();
    }

    //@Override
    public boolean keyUp(InputEvent event, int keycode)
    {
        moveState = PlayerMoveState.IDLE;
        return true;
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
        if (timer > 0) timer--;

        //if get data == ..
        /*if(Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && moveCooldownTimer <= 0) {
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
        }*/
    }

    private void sendControlInput(byte input){
        try{
            //todo: fix JSON Object in OnlineCar
            //move this to StreamController
            //JSONObject data = new JSONObject();
            //data.put(input);
            WarpController.getInstance().sendGameUpdate(Integer.toString(input)); //works??
        }
        catch (Exception e) {

        }
    }

    @Override
    public void onWaitingStarted(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGameStarted(String message) {

    }

    @Override
    public void onGameFinished(int code, boolean isRemote) {

    }

    @Override
    public void onGameUpdateReceived(String message) {
        try {
            moveTo((byte)(Integer.parseInt(message)));
        }
        catch (Exception e){

        }
    }
}
