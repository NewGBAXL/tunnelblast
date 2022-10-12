package com.newgbaxl.blastmaze;

import com.badlogic.gdx.graphics.Color;

public class Cell {
    public byte nWall = 0;
    public byte eWall = 0;
    public byte sWall = 0;
    public byte wWall = 0;
    int col, row;
    Color wallColor;
    //int cellSpd=10; //planned feature; works with "oilSlick"

    public Cell(int nCol, int nRow) {
        col = nCol;
        row = nRow;
    }

    public boolean buildWall(byte wallId) {
        return buildWall(wallId, 1);
    }

    public boolean buildWall(byte wallId, int str) {
        //change implementation to switch?
        //currently allows negative str, helpful for initializing outside walls
        //error, fix asap where str == -1 return false
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
        //check wallId, then check if str >= the current str of the wall
        //troubleshoot
        //also check if is outside wall
        if (wallId == 0){
            nWall = (nWall>=str)? (byte) (nWall - str) :0;
        }
        else if (wallId == 1){
            eWall = (eWall>=str)? (byte) (eWall - str) :0;
        }
        else if (wallId == 2){
            sWall = (sWall>=str)? (byte) (sWall - str) :0;
        }
        else if (wallId == 3) {
            wWall = (wWall>=str)? (byte) (wWall - str) :0;
        }
        return false;
    }
}