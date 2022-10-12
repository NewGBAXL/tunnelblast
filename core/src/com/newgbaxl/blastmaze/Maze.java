package com.newgbaxl.blastmaze;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.newgbaxl.blastmaze.objects.Car;
import com.newgbaxl.blastmaze.objects.UserCar;

import java.util.LinkedList;

public class Maze {

	//World parameters
	public static Cell[][] cells;
	int xWidth = 15;
	int yHeight = 15;
	public static float cellSize, hMargin, vMargin;
	private final float WORLD_WIDTH = 72;
	private final float WORLD_HEIGHT = 128;
	private final float TOUCH_MOVEMENT_THRESHOLD = 5f;

	//Heads-Up Display
	BitmapFont font;
	float hudVerticalMargin, hudLeftX, hudRightX, hudCenterX, hudRow1Y, hudRow2Y, hudSectionWidth;

	//Game objects
	public UserCar player;
	private LinkedList<Car> enemyCarList;

	//these variables need to be moved to their respective classes






	//provided
	public enum PLACE {wall, empty, visited};
	private PLACE maze[][] = new PLACE[Const.MAZE_HEIGHT][Const.MAZE_WIDTH];

	public String toString() {
		return new MazeStringRenderer(this).toString();
	}

	public static boolean isValidPlace(int row, int col) {
		return
			((row >= 0) && (row < Const.MAZE_HEIGHT)) &&
			((col >= 0) && (col < Const.MAZE_WIDTH));
	}

	public void setPlace(int row, int col, PLACE place) {
		if (isValidPlace(row, col)) {
			maze[row][col] = place;
		}
	}

	public PLACE getPlace(int row, int col) {
		PLACE place = null;

		if (isValidPlace(row, col)) {
			place = maze[row][col];
		}

		return place;
	}

}
