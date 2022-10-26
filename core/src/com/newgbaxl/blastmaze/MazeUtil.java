package com.newgbaxl.blastmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MazeUtil {

	public static int GetWallStrength(int x, int y, int direction)
	{
		GridCell[][] grid = MazeScreen2d.getInstance.mazeGrid;
		if (x < 0 || y < 0 || x > Const.MAZE_WIDTH || y > Const.MAZE_HEIGHT) return 0;

		//Prioritize the top right of any cell
		if (direction == 2 && y > 0)
		{
			y--;
			direction = 0;
		}
		if (direction == 3 && x > 0)
		{
			x--;
			direction = 1;
		}

		if (direction == 0) return grid[x][y].nWall;
		if (direction == 1) return grid[x][y].eWall;
		if (direction == 2) return grid[x][y].sWall;
		if (direction == 3) return grid[x][y].wWall;
		return 0;
	}

	public static void SetWallStrength(int x, int y, int direction, int value)
	{
		GridCell[][] grid = MazeScreen2d.getInstance.mazeGrid;
		if (x < 0 || y < 0 || x > Const.MAZE_WIDTH || y > Const.MAZE_HEIGHT) return;

		//Prioritize the top right of any cell
		if (direction == 2 && y > 0)
		{
			y--;
			direction = 0;
		}
		if (direction == 3 && x > 0)
		{
			x--;
			direction = 1;
		}

		if (direction == 0) grid[x][y].nWall = (byte)value;
		if (direction == 1) grid[x][y].eWall = (byte)value;
		if (direction == 2) grid[x][y].sWall = (byte)value;
		if (direction == 3) grid[x][y].wWall = (byte)value;
	}

	public static GridCell GetCellFromDirection(int x, int y, int direction)
	{
		GridCell[][] mazeGrid = MazeScreen2d.getInstance.mazeGrid;

		if (direction == 0)
		{
			if (y >= Const.MAZE_HEIGHT - 1) return null;
			else return mazeGrid[x][y + 1];
		}
		if (direction == 1)
		{
			if (x >= Const.MAZE_WIDTH - 1) return null;
			else return mazeGrid[x + 1][y];
		}
		if (direction == 2)
		{
			if (y <= 0) return null;
			else return mazeGrid[x][y - 1];
		}
		if (direction == 3)
		{
			if (x <= 0) return null;
			else return mazeGrid[x - 1][y];
		}
		return null;
	}

	public static byte GetCellData(int x, int y)
	{
		GridCell[][] grid = MazeScreen2d.getInstance.mazeGrid;
		if (x < 0 || y < 0 || x > Const.MAZE_WIDTH || y > Const.MAZE_HEIGHT) return (byte)0;

		return grid[x][y].cellData;
	}

	public static void SetCellData(int x, int y, byte value)
	{
		GridCell[][] grid = MazeScreen2d.getInstance.mazeGrid;
		if (x < 0 || y < 0 || x > Const.MAZE_WIDTH || y > Const.MAZE_HEIGHT) return;

		grid[x][y].cellData = value;
	}
















	//provided

	public static Rectangle getRoomBoundary(float x, float y) {
		Vector2 v = getRoomOuterLeftBottom(x, y);
		Rectangle boundary = new Rectangle(v.x, v.y, getRoomOuterWidth(),
				getRoomOuterHeight());

		return boundary;
	}

	public static Vector2 getRoomCameraPosition(float x, float y) {
		Vector2 v = getRoomOuterLeftBottom(x, y);

		v.add(getRoomOuterWidth() / 2, getRoomOuterHeight() / 2);

		return v;
	}

	public static Rectangle getRoomInnerBoundary(float x, float y) {
		Rectangle boundary = getRoomBoundary(x, y);

		return new Rectangle(boundary.x + Const.TILE_SIZE - 1, boundary.y
				+ Const.TILE_SIZE - 1, boundary.width - Const.TILE_SIZE + 1,
				boundary.height - Const.TILE_SIZE + 1);
	}

	public static int getRoomOuterHeight() {
		return Const.TILE_SIZE * (Const.MAZE_MAGNIFY_TO_WORLD + 1);
	}

	public static Vector2 getRoomOuterLeftBottom(float x, float y) {
		y -= Const.TILE_SIZE / 2;
		x -= Const.TILE_SIZE / 2;
		int height = (getRoomOuterHeight() - Const.TILE_SIZE);
		int width = (getRoomOuterWidth() - Const.TILE_SIZE);

		x = (float) (Math.floor(x / width) * width);
		y = (float) (Math.floor(y / height) * height);

		y -= Const.TILE_SIZE / 2;
		x -= Const.TILE_SIZE / 2;

		return new Vector2(x, y);
	}

	public static int getRoomOuterWidth() {
		return Const.TILE_SIZE * (Const.MAZE_MAGNIFY_TO_WORLD + 1);
	}

	/**
	 * Returns with the tile's bottom-left world coordinate
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Vector2 getTileCoordinate(float x, float y) {
		Place p = getTilePlace(x, y);

		return getTileCoordinate(p.row, p.col);
	}

	public static Vector2 getTileCoordinate(int row, int col) {
		Vector2 v =
			new Vector2(col * Const.TILE_SIZE, row * Const.TILE_SIZE);

		return v;
	}

	public static Place getTilePlace(float x, float y) {
		Place p = new Place(0, 0);

		p.col = (int) Math.floor(x / Const.TILE_SIZE);
		p.row = (int) Math.floor(y / Const.TILE_SIZE);

		return p;
	}

	public static boolean isDoor(TiledMap map, float x, float y) {
		Place place = getTilePlace(x, y);

		return isDoor(map, place);

	};

	public static boolean isDoor(TiledMap map, Place place) {
		if (!isEmptyCell(map, place)) {
			return false;
		}

		boolean onWall =
			((place.row % Const.MAZE_MAGNIFY_TO_WORLD) == 0) ||
			((place.col % Const.MAZE_MAGNIFY_TO_WORLD) == 0);

		Gdx.app.log("MazeUtil", "isDoor:" + place + "," + onWall);

		return onWall;

	}

	public static boolean isEmptyCell(TiledMap map, Place place) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		//Cell cell = layer.getCell(place.col, place.row);

		//return (cell == null);
		return false;
	}

	public static boolean isEmptyCell(TiledMap map, float worldX, float worldY) {
		Place p = getTilePlace(worldX, worldY);

		return isEmptyCell(map, p);
	}

	public static Vector2 getRoomCameraPosition(Place goal) {
		int row = (goal.row / Const.MAZE_MAGNIFY_TO_WORLD) * Const.MAZE_MAGNIFY_TO_WORLD;
		int col = (goal.col / Const.MAZE_MAGNIFY_TO_WORLD) * Const.MAZE_MAGNIFY_TO_WORLD;

		Vector2 v =
			getTileCoordinate(
				(int)(row + Const.MAZE_MAGNIFY_TO_WORLD / 2) ,
				(int)(col + Const.MAZE_MAGNIFY_TO_WORLD / 2));

		//Camera should be in the center, not at the bottom-left corner of
		//the center tile
		v.add(Const.TILE_SIZE /2, Const.TILE_SIZE / 2);
		return v;
	}

	public static Place stepToDirection(TiledMap map, Direction direction, Place place) {

		if (direction == Direction.NONE) {
			return null;
		}

		Place goal = new Place(place.row, place.col);

		if (direction == Direction.DOWN) {
			goal.row -= 1;
		}
		if (direction == Direction.UP) {
			goal.row += 1;
		}
		if (direction == Direction.LEFT) {
			goal.col -= 1;
		}
		if (direction == Direction.RIGHT) {
			goal.col += 1;
		}

		if (!MazeUtil.isEmptyCell(map, goal)) {
			return null;
		}

		return goal;
	}

}
