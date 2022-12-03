package com.newgbaxl.blastmaze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Locale;

public class StreamController implements Screen {

    Texture img;

    //todo: dump in touch controls
    //todo: dump in HUD

    int bombs = 0;
    int blocks = 0;
    int timer = 0;
    int power = 0;
    int coinsCollected = 0;

    String displayText = "Connecting to server..";

    private SpriteBatch batch;
    private SpriteBatch UISpritebatch;

    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCenterX, hudRow1Y, hudRow2Y, hudSectionWidth;

    public StreamController(){
        super();
        setupScreen();
    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
        updateAndRenderHUD();
    }

    public void setupScreen(){
        batch = new SpriteBatch();
        UISpritebatch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        prepareHUD(115,32);
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

    public void updateAndRenderHUD(){

        UISpritebatch.begin();
        font.draw(UISpritebatch, "Bombs", 10, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(UISpritebatch, "Blocks", 400, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(UISpritebatch, "Power", 800, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(UISpritebatch, "Timer", 1150, hudRow1Y, hudSectionWidth, Align.left, false);

        double timerDisplay = timer / 60f; //todo: change this based on the current mode
        font.draw(UISpritebatch, String.format(Locale.getDefault(), "%02d", bombs), 200, hudRow1Y, hudSectionWidth, Align.right, false);
        font.draw(UISpritebatch, String.format(Locale.getDefault(), "%02d", blocks), 600, hudRow1Y, hudSectionWidth, Align.right, false);
        font.draw(UISpritebatch, String.format(Locale.getDefault(), "%02d", power), 1000, hudRow1Y, hudSectionWidth, Align.right, false);
        font.draw(UISpritebatch, String.format(Locale.getDefault(), "%6.2f", timerDisplay), 1300, hudRow1Y, hudSectionWidth, Align.left, false);

        font.draw(UISpritebatch, displayText, 900, 480, hudSectionWidth, Align.right, false);
        UISpritebatch.end();

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
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        //Stage's InputAdapters was not called with keyboard events.
        //I think, it is logical, because keyboard events are global
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
