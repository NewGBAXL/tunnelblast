package com.newgbaxl.blastmaze.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.newgbaxl.blastmaze.MazeScreen2d;
import com.newgbaxl.blastmaze.MazeUtil;

/**
 * brentaureli 2015
 * NewGBAXL 2022
 * Helper function for controller touch input
 */
public class TouchController {
    Viewport viewport;
    Stage stage;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;

    boolean bombPressed = false;
    boolean buildUpPressed = false;
    boolean buildDownPressed = false;
    boolean buildLeftPressed = false;
    boolean buildRightPressed = false;

    OrthographicCamera cam;

    public TouchController(){
        cam = (OrthographicCamera) MazeUtil.getStage().getCamera();
        viewport = MazeScreen2d.getInstance.mapViewport;//new FitViewport(800, 480, cam);
        stage = MazeUtil.getStage();

        stage.addListener(new InputListener(){

            /*@Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                }
                return true;
            }*/

            /*@Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                }
                return true;
            }*/
        });

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();
        Table table2 = new Table();
        table2.right().bottom();


        Image upImg = new Image(new Texture("TouchUp.png"));
        upImg.setSize(110, 110);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImg = new Image(new Texture("TouchDown.png"));
        downImg.setSize(110, 110);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("TouchRight.png"));
        rightImg.setSize(110, 110);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("TouchLeft.png"));
        leftImg.setSize(110, 110);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image leftBuildImg = new Image(new Texture("TouchLeft.png"));
        leftBuildImg.setSize(110, 110);
        leftBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildLeftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buildLeftPressed = false;
            }
        });

        Image rightBuildImg = new Image(new Texture("TouchLeft.png"));
        rightBuildImg.setSize(110, 110);
        rightBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildRightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buildRightPressed = false;
            }
        });

        Image upBuildImg = new Image(new Texture("TouchLeft.png"));
        upBuildImg.setSize(110, 110);
        upBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildUpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buildUpPressed = false;
            }
        });

        Image downBuildImg = new Image(new Texture("TouchLeft.png"));
        downBuildImg.setSize(110, 110);
        downBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildDownPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buildDownPressed = false;
            }
        });

        Image bombImg = new Image(new Texture("TouchLeft.png"));
        bombImg.setSize(110, 110);
        bombImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bombPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bombPressed = false;
            }
        });

        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());

        table2.add();
        table2.add(upBuildImg).size(upBuildImg.getWidth(), upBuildImg.getHeight());
        table2.add();
        table2.row().pad(5, 5, 5, 5);
        table2.add(leftBuildImg).size(leftBuildImg.getWidth(), leftBuildImg.getHeight());
        table2.add();
        table2.add(rightBuildImg).size(rightBuildImg.getWidth(), rightBuildImg.getHeight());
        table2.row().padBottom(5);
        table2.add();
        table2.add(downBuildImg).size(downBuildImg.getWidth(), downBuildImg.getHeight());
        table2.add();
        table2.add(bombImg).size(bombImg.getWidth(), bombImg.getHeight());
        table2.add();

        stage.addActor(table);
        stage.addActor(table2);
    }

    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isBuildUpPressed() {
        return buildUpPressed;
    }

    public boolean isBuildDownPressed() {
        return buildDownPressed;
    }

    public boolean isBuildLeftPressed() {
        return buildLeftPressed;
    }

    public boolean isBuildRightPressed() {
        return buildRightPressed;
    }

    public boolean isBombPressed() {
        return bombPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}

/*if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game));
				return;
			}
			if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HighscoresScreen(game));
				return;
			}
			if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen(game));
				return;
			}
			if (multiplayerBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				WarpController.getInstance().startApp(getRandomHexString(10));
				game.setScreen(new StartMultiplayerScreen(game));
				return;
			}
			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
			}
		}*/
