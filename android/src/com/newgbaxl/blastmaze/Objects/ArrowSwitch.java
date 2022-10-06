package com.newgbaxl.blastmaze.Objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;

import com.newgbaxl.blastmaze.Map;
import com.newgbaxl.blastmaze.R;

public class ArrowSwitch extends CompoundButton {
    //could alternatively extend Switch??
    public Drawable arrowLeft;
    public Drawable arrowRight;
    public Drawable options;
    public Map game;

    public ArrowSwitch(Context context) {
        super(context);
    }

    //remove this constructor
    public ArrowSwitch(Context context, Map game) {
        super(context);
        this.game = game;
    }

    //this will create our custom better switches for Settings
    //todo: create parent or child class for Shop


    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    public void switchView(){
        if (isChecked())
            options = game.getResources().getDrawable(R.drawable.onselected, null);
        else
            options = game.getResources().getDrawable(R.drawable.offselected, null);
    }

}
