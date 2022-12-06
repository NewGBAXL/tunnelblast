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

    final int inputBuffer = 3;

    int upPressed = 0;
    int downPressed = 0;
    int leftPressed = 0;
    int rightPressed = 0;

    int bombPressed = 0;
    int buildUpPressed = 0;
    int buildDownPressed = 0;
    int buildLeftPressed = 0;
    int buildRightPressed = 0;

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
        table2.bottom().right();
        table2.setPosition(1300, 0);
        table.setColor(1, 1, 1, 0.5f);
        table2.setColor(1, 1, 1, 0.5f);


        Image upImg = new Image(new Texture("TouchUp.png"));
        upImg.setSize(110, 110);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    upPressed = false;
            //}
        });

        Image downImg = new Image(new Texture("TouchDown.png"));
        downImg.setSize(110, 110);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    downPressed = false;
            //}
        });

        Image rightImg = new Image(new Texture("TouchRight.png"));
        rightImg.setSize(110, 110);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    rightPressed = false;
            //}
        });

        Image leftImg = new Image(new Texture("TouchLeft.png"));
        leftImg.setSize(110, 110);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    leftPressed = false;
            //}
        });

        Image leftBuildImg = new Image(new Texture("TouchLeft.png"));
        leftBuildImg.setSize(110, 110);
        leftBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildLeftPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    buildLeftPressed = false;
            //}
        });

        Image rightBuildImg = new Image(new Texture("TouchLeft.png"));
        rightBuildImg.setSize(110, 110);
        rightBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildRightPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    buildRightPressed = false;
            //}
        });

        Image upBuildImg = new Image(new Texture("TouchLeft.png"));
        upBuildImg.setSize(110, 110);
        upBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildUpPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    buildUpPressed = false;
            //}
        });

        Image downBuildImg = new Image(new Texture("TouchLeft.png"));
        downBuildImg.setSize(110, 110);
        downBuildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildDownPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    buildDownPressed = false;
            //}
        });

        Image bombImg = new Image(new Texture("TouchLeft.png"));
        bombImg.setSize(110, 110);
        bombImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    bombPressed = inputBuffer;
                return true;
            }

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    bombPressed = false;
            //}
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
        return upPressed > 0;
    }

    public boolean isDownPressed() {
        return downPressed > 0;
    }

    public boolean isLeftPressed() {
        return leftPressed > 0;
    }

    public boolean isRightPressed() {
        return rightPressed > 0;
    }

    public boolean isBuildUpPressed() {
        return buildUpPressed > 0;
    }

    public boolean isBuildDownPressed() {
        return buildDownPressed > 0;
    }

    public boolean isBuildLeftPressed() {
        return buildLeftPressed > 0;
    }

    public boolean isBuildRightPressed() {
        return buildRightPressed > 0;
    }

    public boolean isBombPressed() {
        return bombPressed > 0;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public void Update()
    {
        if (upPressed > 0) upPressed--;
        if (downPressed > 0) downPressed--;
        if (leftPressed > 0) leftPressed--;
        if (rightPressed > 0) rightPressed--;
        if (bombPressed > 0) bombPressed--;
        if (buildUpPressed > 0) buildUpPressed--;
        if (buildDownPressed > 0) buildDownPressed--;
        if (buildLeftPressed > 0) buildLeftPressed--;
        if (buildRightPressed > 0) buildRightPressed--;
    }

    public void ClearBuffers()
    {
        if (upPressed > 0) upPressed = 0;
        if (downPressed > 0) downPressed = 0;
        if (leftPressed > 0) leftPressed = 0;
        if (rightPressed > 0) rightPressed = 0;
        if (bombPressed > 0) bombPressed = 0;
        if (buildUpPressed > 0) buildUpPressed = 0;
        if (buildDownPressed > 0) buildDownPressed = 0;
        if (buildLeftPressed > 0) buildLeftPressed = 0;
        if (buildRightPressed > 0) buildRightPressed = 0;
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
