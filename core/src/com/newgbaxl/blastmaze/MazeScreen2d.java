package com.newgbaxl.blastmaze;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.newgbaxl.blastmaze.camera.MapViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.newgbaxl.blastmaze.objects.UserCar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MazeScreen2d implements Screen {

	private SpriteBatch batch;

	private Stage stage;

	private TiledMapRenderer mazeRenderer;

	private CameraInputAdapter camInput;

	private MapViewport mapViewport;

	private TiledMapRenderer mapRenderer;
	UserCar user;

	//Array of grid spaces, walls defined by digits in hexadecimal format
	public GridCell[][] mazeGrid;
	public Texture mazeWall = new Texture("brickWallDirectional.png");

	public static MazeScreen2d getInstance;

	//Heads-Up Display
	BitmapFont font;
	float hudVerticalMargin, hudLeftX, hudRightX, hudCenterX, hudRow1Y, hudRow2Y, hudSectionWidth;

	public MazeScreen2d() {
		super();
		getInstance = this;
		Maze maze = (new MazeCreator()).getMaze();
	
		MazeTileRenderer mTileRenderer =
			new MazeTileRenderer(maze, "brick_wall_single_perfect.png");

		mazeRenderer = mTileRenderer.getRenderer();

		MazeMapRenderer mMapRenderer =
			new MazeMapRenderer(maze, "brick_wall_single_perfect.png", 2);

		mapRenderer = mMapRenderer.getRenderer();

		TiledMap tiledMap = mTileRenderer.getMap();

		camInput = new CameraInputAdapter();
		Actor bobActor = new BobActor(camInput, tiledMap);
		Actor cameraActor =
			new CameraActor(tiledMap, (BobActor) bobActor);
		ScreenViewport svp = new ScreenViewport();
		svp.update(Const.TILE_SIZE * Const.MAZE_MAGNIFY_TO_WORLD,
				Const.TILE_SIZE * Const.MAZE_MAGNIFY_TO_WORLD, true);

		mapViewport = new MapViewport(Const.TILE_SIZE * Const.MAZE_WIDTH,
				Const.TILE_SIZE * Const.MAZE_HEIGHT);

		stage = new Stage(svp);
		stage.addActor(cameraActor);
		//Actor bouncers[] = new Actor[10];
		//for (int i = 0; i < bouncers.length; i+= 2) {
		//	bouncers[i] = new Bouncer(32, 32, getRandomColor(), (float)(Math.random() * 0.6) + 0.1f, tiledMap);
		//	bouncers[i + 1] = new Fly(32, 32, getRandomColor(), (float)(Math.random() * 0.6) + 0.1f, tiledMap);
		//	stage.addActor(bouncers[i]);
		//	stage.addActor(bouncers[i + 1]);
		//}

		user = new UserCar(32,32, getRandomColor(),
				(float)(Math.random() * 0.6) + 0.1f, (byte)1, (byte)1);

		stage.addActor(user);

		GenerateMaze();

		prepareHUD(115, 32);

		batch = new SpriteBatch();
	}

	private Color getRandomColor() {
		Color colors[] = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA};
		return colors[MathUtils.random(colors.length - 1)];
	}

	public void prepareHUD(int width, int height) {
		//Create a BitmapFont from our font file
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		fontParameter.size = 72;
		fontParameter.borderWidth = 3.6f;
		fontParameter.color = new Color(1, 1, 1, 0.3f);
		fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

		font = fontGenerator.generateFont(fontParameter);

		//scale the font to fit world
		font.getData().setScale(0.8f);

		//calculate hud margins, etc.
		hudVerticalMargin = font.getCapHeight() / 2;
		hudLeftX = hudVerticalMargin;
		hudRightX = width * 2 / 3 - hudLeftX;
		hudCenterX = height / 3;
		hudRow1Y = 700; //height - hudVerticalMargin;
		hudRow2Y = 630; //hudRow1Y - hudVerticalMargin - font.getCapHeight();
		hudSectionWidth = width / 3;
	}

	private void updateAndRenderHUD() {
		//For potentially rendering in portrait mode
		/*//render top row labels
		font.draw(batch, "Bombs", 100, hudRow1Y, hudSectionWidth, Align.center, false);
		font.draw(batch, "Blocks", 500, hudRow1Y, hudSectionWidth, Align.center, false);
		font.draw(batch, "Power", 900, hudRow1Y, hudSectionWidth, Align.center, false);
		font.draw(batch, "Timer", 1300, hudRow1Y, hudSectionWidth, Align.center, false);

		//render second row values
		font.draw(batch, String.format(Locale.getDefault(), "%02d", user.bombs), 100, hudRow2Y, hudSectionWidth, Align.center, false);//hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", user.blocks), 500, hudRow2Y, hudSectionWidth, Align.center, false);;//hudCenterX, hudRow2Y, hudSectionWidth, Align.center, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", user.power), 900, hudRow2Y, hudSectionWidth, Align.center, false);//hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
		font.draw(batch, String.format(Locale.getDefault(), "%.2f", timer), 1300, hudRow2Y, hudSectionWidth, Align.center, false);//hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
		*/

		batch.begin();
		font.draw(batch, "Bombs", 10, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(batch, "Blocks", 400, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(batch, "Power", 800, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(batch, "Timer", 1150, hudRow1Y, hudSectionWidth, Align.left, false);

		font.draw(batch, String.format(Locale.getDefault(), "%02d", user.bombs), 200, hudRow1Y, hudSectionWidth, Align.right, false);//hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", user.blocks), 600, hudRow1Y, hudSectionWidth, Align.right, false);;//hudCenterX, hudRow2Y, hudSectionWidth, Align.center, false);
		font.draw(batch, String.format(Locale.getDefault(), "%02d", user.power), 1000, hudRow1Y, hudSectionWidth, Align.right, false);//hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
		font.draw(batch, String.format(Locale.getDefault(), "%6.2f", user.timer), 1400, hudRow1Y, hudSectionWidth, Align.right, false);//hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);


		font.draw(batch, "FPS", 50, 480, hudSectionWidth, Align.right, false);
		font.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 110, 480);
		batch.end();
	}

	@Override
	public void show() {
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(camInput);
		//Stage's InputAdapters was not called with keyboard events.
		//I think, it is logical, because keyboard events are global
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
	}

	//@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();

		user.setPosition(w/2 -user.getWidth()/2, h/2 - user.getHeight()/2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)) GenerateMaze();

		stage.act(delta);
		stage.getViewport().apply();
		stage.draw();

		OrthographicCamera cam = (OrthographicCamera) stage.getCamera();

		//mazeRenderer.setView(cam);
		//mazeRenderer.render();

		//mapViewport.apply();
		//mapRenderer.setView((OrthographicCamera)mapViewport.getCamera());
		//mapRenderer.render();

		//batch.begin();
		//user.draw(batch, 1);
		//batch.end();

		//Draw walls
		batch.begin();
		for (int x = 0; x < Const.MAZE_WIDTH; x++) {
			for (int y = 0; y < Const.MAZE_HEIGHT; y++) {
				if (mazeGrid[x][y].nWall != 0)
					batch.draw(mazeWall, x * Const.TILE_SIZE - 16, (y + 0.5f) * Const.TILE_SIZE - 16, 0, 0, Const.TILE_SIZE, Const.TILE_SIZE, 1, 1, 0, 0, 0, 48, 32, false, false);
				if (mazeGrid[x][y].eWall != 0)
					batch.draw(mazeWall, (x + 1.5f) * Const.TILE_SIZE - 16, y * Const.TILE_SIZE - 16, 0, 0, Const.TILE_SIZE, Const.TILE_SIZE, 1, 1, 90, 0, 0, 48, 32, false, false);
				if (mazeGrid[x][y].sWall != 0)
					batch.draw(mazeWall, x * Const.TILE_SIZE - 16, (y - 0.5f) * Const.TILE_SIZE - 16, 0, 0, Const.TILE_SIZE, Const.TILE_SIZE, 1, 1, 0, 0, 0, 48, 32, false, false);
				if (mazeGrid[x][y].wWall != 0)
					batch.draw(mazeWall, (x + 0.5f) * Const.TILE_SIZE - 16, y * Const.TILE_SIZE - 16, 0, 0, Const.TILE_SIZE, Const.TILE_SIZE, 1, 1, 90, 0, 0, 48, 32, false, false);
			}
		}
		batch.end();

		updateAndRenderHUD();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		mapViewport.update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	public void GenerateMaze()
	{
		mazeGrid = new GridCell[Const.MAZE_WIDTH][Const.MAZE_WIDTH];
		Random rand = new Random();

		boolean[][] used = new boolean[Const.MAZE_WIDTH][Const.MAZE_HEIGHT];
		for (int x = 0; x < Const.MAZE_WIDTH; x++) for (int y = 0; y < Const.MAZE_HEIGHT; y++)
		{
			mazeGrid[x][y] = new GridCell(x, y);
			if (x == 0) mazeGrid[x][y].wWall = -2;
			if (x == Const.MAZE_WIDTH - 1) mazeGrid[x][y].eWall = -2;
			if (y == 0) mazeGrid[x][y].sWall = -2;
			if (y == Const.MAZE_HEIGHT - 1) mazeGrid[x][y].nWall = -2;

			used[x][y] = false;
		}

		ArrayList<GridCell> cells = new ArrayList<>();
		cells.add(mazeGrid[Const.SPAWN_CELL_X][Const.SPAWN_CELL_Y]);

		int iteration = 0;
		while (!cells.isEmpty())
		{
			GridCell c = cells.get(0);

			//Get possible directions for the cell to go
			ArrayList<Integer> directions = new ArrayList<>();
			for (int i = 0; i <= 3; i++)
			{
				GridCell c2 = MazeUtil.GetCellFromDirection(c.x, c.y, i);
				if (c2 != null && !used[c2.x][c2.y]) directions.add(i);
			}

			if (!directions.isEmpty())
			{
				//Pick a direction to extend the maze
				int dir = directions.get(rand.nextInt(directions.size()));
				directions.remove((Object) dir);

				//Add new direction to the cell list
				cells.add(MazeUtil.GetCellFromDirection(c.x, c.y, dir));

				//Place walls on remaining sides
				for (int i : directions) MazeUtil.SetWallStrength(c.x, c.y, i, 1);
			}

			cells.remove(0);
			used[c.x][c.y] = true;

			iteration++;
			if (iteration > 20) break;
		}
	}
}