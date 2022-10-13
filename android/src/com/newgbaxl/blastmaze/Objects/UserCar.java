package com.newgbaxl.blastmaze.Objects;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.InputDevice;
import android.view.MotionEvent;

import androidx.core.content.res.ResourcesCompat;

import com.newgbaxl.blastmaze.Objects.Car;
import com.newgbaxl.blastmaze.Dpad;
import com.newgbaxl.blastmaze.Map;
import com.newgbaxl.blastmaze.R;

public class UserCar extends Car {
    //we might not even need this class
    int xPos = 0;
    int yPos = 0;
    Dpad dpad;
    //public boolean moving = false;
    public UserCar(Map game, int x, int y, Paint nSkin, byte nBaseSpd, byte nPwrRate) {
        super(game, x, y, nSkin, nBaseSpd, nPwrRate);
        nSkin.setColor(Color.GREEN);
        dpad = new Dpad();
        sprite = ResourcesCompat.getDrawable(game.getResources(), R.drawable.car2, null);
    }

    @Override
    public void Update() {
        //add timeout, this should fix build/break

        //move
        if (game.UIBinding.upButton.isPressed()) moveTo((byte) 0);
        if (game.UIBinding.downButton.isPressed()) moveTo((byte) 2);
        if (game.UIBinding.leftButton.isPressed()) moveTo((byte) 3);
        if (game.UIBinding.rightButton.isPressed()) moveTo((byte) 1);


        if (game.UIBinding.buildButton.isPressed()) build((byte)0);
        /*//destroy
        if (game.UIBinding.upButton.isPressed() && game.UIBinding.breakButton.isPressed()) moveTo((byte) 0);
        if (game.UIBinding.downButton.isPressed() && game.UIBinding.breakButton.isPressed()) moveTo((byte) 2);
        if (game.UIBinding.leftButton.isPressed() && game.UIBinding.breakButton.isPressed()) moveTo((byte) 3);
        if (game.UIBinding.rightButton.isPressed() && game.UIBinding.breakButton.isPressed()) moveTo((byte) 1);

        //build
        if (game.UIBinding.upButton.isPressed() && game.UIBinding.buildButton.isPressed()) moveTo((byte) 0);
        if (game.UIBinding.downButton.isPressed() && game.UIBinding.buildButton.isPressed()) moveTo((byte) 2);
        if (game.UIBinding.leftButton.isPressed() && game.UIBinding.buildButton.isPressed()) moveTo((byte) 3);
        if (game.UIBinding.rightButton.isPressed() && game.UIBinding.buildButton.isPressed()) moveTo((byte) 1);
        /*
        //evade
        //if (game.UIBinding.evadeButton.isPressed() oilSlick(1);
        //planned feature*/
        super.Update();
    }

    public boolean destroy(byte cardinal, int str)
    {
        boolean returnBool = super.destroy(cardinal, str);
        game.bombsCounter.setValue(bombs);
        return returnBool;
    }

    public boolean build(byte cardinal)
    {
        boolean returnBool = super.build(cardinal);
        game.blocksCounter.setValue(blocks);
        return returnBool;
    }

    //use power
    //timer
    //..

    private void processJoystickInput(MotionEvent event, int historyPos) {
        InputDevice inputDevice = event.getDevice();

    }

    /*public void onGenericMotionEvent(MotionEvent event) {

        // Check if this event if from a D-pad and process accordingly.
        if (Dpad.isDpadDevice(event)) {
            int press = dpad.getDirectionPressed(event);
            moveTo((byte) press);
            //moving = true;
        }
    }*/
        // Check if this event is from a joystick movement and process accordingly.
}

