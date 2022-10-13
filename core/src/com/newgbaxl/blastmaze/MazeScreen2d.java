package com.newgbaxl.blastmaze;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.newgbaxl.blastmaze.actors.Bouncer;
import com.newgbaxl.blastmaze.actors.Fly;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.newgbaxl.blastmaze.objects.UserCar;

public class MazeScreen2d implements Screen {

	private SpriteBatch batch;

	private Stage stage;

	private TiledMapRenderer mazeRenderer;

	private CameraInputAdapter camInput;

	private MapViewport mapViewport;

	private TiledMapRenderer mapRenderer;
	UserCar user;

	public MazeScreen2d() {
		super();
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
		Gdx.app.log("MazeScreen2d","bob is adding to stage");
		stage.addActor(bobActor);
		Gdx.app.log("MazeScreen2d","bob adding done");
		Actor bouncers[] = new Actor[10];
		for (int i = 0; i < bouncers.length; i+= 2) {
			bouncers[i] = new Bouncer(32, 32, getRandomColor(), (float)(Math.random() * 0.6) + 0.1f, tiledMap);
			bouncers[i + 1] = new Fly(32, 32, getRandomColor(), (float)(Math.random() * 0.6) + 0.1f, tiledMap);
			stage.addActor(bouncers[i]);
			stage.addActor(bouncers[i + 1]);
		}

		user = new UserCar(32,32, getRandomColor(),
				(float)(Math.random() * 0.6) + 0.1f, (byte)1, (byte)1);
	}

	private Color getRandomColor() {
		Color colors[] = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA};
		return colors[MathUtils.random(colors.length - 1)];
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

		stage.act(delta);
		stage.getViewport().apply();
		stage.draw();

		OrthographicCamera cam = (OrthographicCamera) stage.getCamera();

		mazeRenderer.setView(cam);
		mazeRenderer.render();

		mapViewport.apply();
		mapRenderer.setView((OrthographicCamera)mapViewport.getCamera());
		mapRenderer.render();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			//if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				user.moveTo((byte)3);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			//if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				user.moveTo((byte)1);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			//if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				user.moveTo((byte)0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			//if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				user.moveTo((byte)2);
		}

		batch.begin();
		user.draw(batch, 1);
		batch.end();
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

}