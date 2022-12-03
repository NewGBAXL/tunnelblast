package com.newgbaxl.blastmaze;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
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
import com.newgbaxl.blastmaze.controller.TouchController;
import com.newgbaxl.blastmaze.objects.EnemyCar;
import com.newgbaxl.blastmaze.objects.UserCar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import sun.rmi.runtime.Log;
import sun.security.util.Debug;

public class MazeScreen2d implements Screen {

	//Data to pass back to the android side
	public static int result = -1;
	public static int currentLvlID = 0;
	public static int coinsCollected = 0;

	private SpriteBatch batch;
	private SpriteBatch UISpritebatch;

	public Stage stage;

	private TiledMapRenderer mazeRenderer;

	public CameraInputAdapter camInput;
	public TouchController controller;
	public MapViewport mapViewport;

	private TiledMapRenderer mapRenderer;
	public UserCar user;
	LinkedList<EnemyCar> enemies;
	EnemyCar enemyTestOnly;
	public float enemySpeedMultiplier = 1f;
	public boolean huntEnemyMode = false;
	public boolean debugMode = true; //shows FPS, etc; toggle in Settings
	public boolean touchControls = true; //toggle in Settings

	public boolean gameLoaded = false;

	//Set in the scenario and only called on map creation
	public int coinCount = 0;

	//Array of grid spaces, walls defined by digits in hexadecimal format
	public GridCell[][] mazeGrid;
	public Texture mazeFloor = new Texture("brickFloor.png");
	public Texture mazeWall = new Texture("brickWallDirectional.png");
	public Texture questionPickup = new Texture("Circle_question_mark.png");
	Map<Short, Texture> pickupImages = new HashMap<Short, Texture>(){{
		put((short)-2, new Texture("Pickup_Block.png"));
		put((short)-3, new Texture("Pickup_Bomb.png"));
		put((short)-6, new Texture("Pickup_Coin.png"));
	}};

	public static MazeScreen2d getInstance;

	public Scenario currentScenario;

	//Heads-Up Display
	BitmapFont font;
	float hudVerticalMargin, hudLeftX, hudRightX, hudCenterX, hudRow1Y, hudRow2Y, hudSectionWidth;

	public MazeScreen2d() {
		super();
		SetupScreen();

		int startingCars = 2;
		for (int i = 0; i < startingCars; ++i)
		{
			enemies.add(new EnemyCar(32,32, getRandomColor(),
					(float)(Math.random() * 0.6) + 0.1f, (byte)1, (byte)1,1));

			stage.addActor(enemies.peekLast());
		}

		PlaceCoins();
	}

	public MazeScreen2d(int carSkin, int special)
	{
		super();
		SetupScreen();

		//todo: apply car skin and special to the user car
		user = new UserCar(32,32, getRandomColor(),
				(float)(Math.random() * 0.6) + 0.1f, (byte)1, (byte)1);
		PlaceCoins();
	}

	public MazeScreen2d(int carSkin, int special, int scenarioID)
	{
		super();
		SetupScreen();

		if (scenarioID >= 0 && scenarioID < Scenario.scenarios.length)
		{
			currentScenario = Scenario.scenarios[scenarioID];
			currentLvlID = scenarioID; //do not use this variable anywhere else
		}
		if (currentScenario != null) currentScenario.OnStart(this);
		PlaceCoins();
	}

	private void SetupScreen()
	{
		result = -1;
		coinsCollected = 0;

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

		GenerateMaze();

		prepareHUD(115, 32);

		batch = new SpriteBatch();
		UISpritebatch = new SpriteBatch();

		user = new UserCar(32,32, getRandomColor(),
				(float)(Math.random() * 0.6) + 0.1f, (byte)1, (byte)1);
		stage.addActor(user);


		enemies = new LinkedList<>();
		controller = new TouchController();
		//gameLoaded = true; //turn this on to enable touch controls
	}

	public void PlaceCoins()
	{
		Random rand = new Random();

		ArrayList<Coordinates> invalidPositions = new ArrayList<>();
		invalidPositions.add(user.position);
		if (enemies != null) for (int e = 0; e < enemies.size(); e++) invalidPositions.add(enemies.get(e).position);

		float tolerance = 16;
		for (int i = 0; i < coinCount; i++)
		{
			boolean placed = false;
			while (!placed)
			{
				Gdx.app.log("maze", "Placed Coin");
				//Pick a random position
				Vector2 pos = new Vector2(rand.nextInt(Const.MAZE_WIDTH), rand.nextInt(Const.MAZE_HEIGHT));

				if (mazeGrid[(int)pos.x][(int)pos.y].cellData != -1) continue;
				for (int p = 0; p < invalidPositions.size(); p++)
				{
					//If it is too close to something, retry and increase tolerance
					float dist = (float)Math.sqrt(Math.pow(invalidPositions.get(p).gridX - pos.x, 2) + Math.pow(invalidPositions.get(p).gridY - pos.y, 2));
					if (dist < tolerance)
					{
						tolerance *= 0.95f;
						continue;
					}
				}

				//Nothing is nearby, place the coin
				placed = true;
				invalidPositions.add(new Coordinates(pos.x * Const.TILE_SIZE, pos.y * Const.TILE_SIZE));
				mazeGrid[(int)pos.x][(int)pos.y].cellData = -6;
			}
		}
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
		fontParameter.borderWidth = 4f;
		fontParameter.color = new Color(1, 1, 1, 0.5f);
		fontParameter.borderColor = new Color(0, 0, 0, 0.5f);

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

		UISpritebatch.begin();
		font.draw(UISpritebatch, "Bombs", 10, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(UISpritebatch, "Blocks", 400, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(UISpritebatch, "Power", 800, hudRow1Y, hudSectionWidth, Align.left, false);
		font.draw(UISpritebatch, "Timer", 1150, hudRow1Y, hudSectionWidth, Align.left, false);

		double timerDisplay = user.timer / 60f; //todo: change this based on the current mode
		font.draw(UISpritebatch, String.format(Locale.getDefault(), "%02d", user.bombs), 200, hudRow1Y, hudSectionWidth, Align.right, false);//hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
		font.draw(UISpritebatch, String.format(Locale.getDefault(), "%02d", user.blocks), 600, hudRow1Y, hudSectionWidth, Align.right, false);;//hudCenterX, hudRow2Y, hudSectionWidth, Align.center, false);
		font.draw(UISpritebatch, String.format(Locale.getDefault(), "%02d", user.power), 1000, hudRow1Y, hudSectionWidth, Align.right, false);//hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
		font.draw(UISpritebatch, String.format(Locale.getDefault(), "%6.2f", timerDisplay), 1300, hudRow1Y, hudSectionWidth, Align.left, false);//hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);

		font.draw(UISpritebatch, "FPS", 50, 480, hudSectionWidth, Align.right, false);
		font.draw(UISpritebatch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 110, 480);

		//for testing
		//font.draw(batch, "UpPriority", 40, 200, hudSectionWidth, Align.right, false);
		//font.draw(batch, String.format(Locale.getDefault(), "%02d", enemyTestOnly.up), 40, 100);
		//font.draw(batch, "RightPriority", 440, 200, hudSectionWidth, Align.right, false);
		//font.draw(batch, String.format(Locale.getDefault(), "%02d", enemyTestOnly.rp), 440, 100);
		//font.draw(batch, "DownPriority", 840, 200, hudSectionWidth, Align.right, false);
		//font.draw(batch, String.format(Locale.getDefault(), "%02d", enemyTestOnly.dp), 840, 100);
		//font.draw(batch, "LeftPriority", 1200, 200, hudSectionWidth, Align.right, false);
		//font.draw(batch, String.format(Locale.getDefault(), "%02d", enemyTestOnly.lp), 1200, 100);

		UISpritebatch.end();
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
		enemyTestOnly.setPosition(w/2 -enemyTestOnly.getWidth()/2, h/2 - enemyTestOnly.getHeight()/2);
	}

	@Override
	public void render(float delta) {
		OrthographicCamera cam = (OrthographicCamera) stage.getCamera();
		cam.translate(620, 300);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Draw the floor of the maze
		batch.begin();
		for (int x = 0; x < Const.MAZE_WIDTH; x++) {
			for (int y = 0; y < Const.MAZE_HEIGHT; y++) {
				batch.draw(mazeFloor, x * Const.TILE_SIZE - 8, y * Const.TILE_SIZE - 8, 0, 0, Const.TILE_SIZE, Const.TILE_SIZE, 1f, 1f, 0, 0, 0, 64, 64, false, false);
			}}
		batch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) GenerateMaze();

		Gdx.input.setOnscreenKeyboardVisible(false);

		stage.act(delta);
		if (currentScenario != null) currentScenario.Update(this);
		if (currentScenario != null && currentScenario.CheckForWin(this))
		{
			//todo: Logic for when you win
			result = currentScenario.getRankResult(this);
			quitGame();
		}
		stage.getViewport().apply();
		stage.draw();

		//stage.getCamera().position.x = 100;


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
		batch.setProjectionMatrix(cam.combined);
		for (int x = 0; x < Const.MAZE_WIDTH; x++) {
			for (int y = 0; y < Const.MAZE_HEIGHT; y++) {
				if (MazeUtil.GetCellData(x, y) <= -2) {
					if (pickupImages.containsKey(MazeUtil.GetCellData(x, y)))
						batch.draw(pickupImages.get(MazeUtil.GetCellData(x, y)), x * Const.TILE_SIZE - 8, y * Const.TILE_SIZE - 8, 0, 0, Const.TILE_SIZE * 0.75f, Const.TILE_SIZE * 0.75f, 1f, 1f, 0, 0, 0, 32, 32, false, false);
					else batch.draw(questionPickup, x * Const.TILE_SIZE, y * Const.TILE_SIZE, 0, 0, Const.TILE_SIZE, Const.TILE_SIZE, 0.5f, 0.5f, 0, 0, 0, 196, 199, false, false);
				}
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

		cam.translate(-cam.position.x, -cam.position.y);
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
			GridCell c = cells.get(cells.size() - 1);

			//Get possible directions for the cell to go
			ArrayList<Integer> directions = new ArrayList<>();
			for (int i = 0; i <= 3; i++)
			{
				GridCell c2 = MazeUtil.GetCellFromDirection(c.x, c.y, i);
				if (c2 != null && !used[c2.x][c2.y]) directions.add(i);
			}

			boolean done = false;
			while (!done) {
				if (!directions.isEmpty()) {
					//Pick a direction to extend the maze
					int dir = directions.get(rand.nextInt(directions.size()));
					directions.remove((Object) dir);

					//Add new direction to the cell list
					cells.add(MazeUtil.GetCellFromDirection(c.x, c.y, dir));
				}
				if (directions.isEmpty() || (rand.nextInt(32) != 0 && cells.indexOf(c) != 0)) done = true;
			}
			//Place walls on remaining sides
			for (int i : directions) MazeUtil.SetWallStrength(c.x, c.y, i, 1);

			if (rand.nextInt(32) == 0 && !(c.x == Const.SPAWN_CELL_X && c.y == Const.SPAWN_CELL_Y)) c.cellData = (short)((rand.nextInt(4) + 2) * -1);
			Gdx.app.log("Tag", "" + c.cellData);

			cells.remove(c);
			used[c.x][c.y] = true;

			//Try to read cells that were missed
			if (cells.isEmpty())
			{
				boolean found = false;
				for (int x = 0; x < Const.MAZE_WIDTH; x++)
				{
					if (found) break;
					for (int y = 0; y < Const.MAZE_HEIGHT; y++)
					{
						if (!used[x][y])
						{
							cells.add(mazeGrid[x][y]);
							found = true;
							break;
						}
					}
				}
			}

			iteration++;
			if (iteration > Const.MAZE_WIDTH * Const.MAZE_HEIGHT * 2) break;
		}
	}

	public void quitGame()
	{
		if (result == -1) result = 0;
		Gdx.app.exit();
	}
}