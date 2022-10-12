package com.newgbaxl.blastmaze;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.newgbaxl.blastmaze.databinding.ActivityUsercarBinding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

//remove after successful merge

public class Cell {
    public byte nWall = 0;
    public byte eWall = 0;
    public byte sWall = 0;
    public byte wWall = 0;
    int col, row;
    private Paint normalPaint;

    public Cell(int nCol, int nRow) {
        col = nCol;
        row = nRow;
    }

    public boolean buildWall(byte wallId) {
        return buildWall(wallId, 1);
    }

    public boolean buildWall(byte wallId, int str) {
        if (wallId == 0 && nWall == 0) {
            nWall = (byte) str;
            return true;
        } else if (wallId == 1 && eWall == 0) {
            eWall = (byte) str;
            return true;
        } else if (wallId == 2 && sWall == 0) {
            sWall = (byte) str;
            return true;
        } else if (wallId == 3 && wWall == 0) {
            wWall = (byte) str;
            return true;
        }
        return false;
    }

    public boolean breakWall(byte wallId, int str) {
        return false;
    }
}

