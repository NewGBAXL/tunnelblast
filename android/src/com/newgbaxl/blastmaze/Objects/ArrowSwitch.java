package com.newgbaxl.blastmaze.Objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import com.newgbaxl.blastmaze.Map;
import com.newgbaxl.blastmaze.R;

public class ArrowSwitch{
    public boolean state;

    public ArrowSwitch(){
        state = true;
    }

    public boolean leftPressedAction(){
        if (state==false){
            state=true;
            return true;
        }
        return false;
    }

    public boolean rightPressedAction(){
        if (state==true){
            state=false;
            return true;
        }
        return false;
    }
}

/*public abstract class ArrowSwitch extends ViewGroup {
    //could alternatively extend Switch??
    public Drawable arrowLeft;
    public Drawable arrowRight;
    public Drawable options;
    public Map game;

    public boolean val;

    public ArrowSwitch(Context context) {
        super(context);
    }

    public ArrowSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*@Override
    public boolean callOnClick() {
        return super.callOnClick();
    }*/

    //if right arrow clicked && val==true
    //png=offselected and val=false

    //if left arrow clicked && val==false
    //png=onselected and val=true

    /*public void switchView(){
        if (isChecked())
            options = game.getResources().getDrawable(R.drawable.onselected, null);
        else
            options = game.getResources().getDrawable(R.drawable.offselected, null);
    }

}*/