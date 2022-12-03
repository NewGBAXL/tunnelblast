package com.newgbaxl.blastmaze.multiplayer;

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
import com.newgbaxl.blastmaze.appwarp.WarpController;
import com.newgbaxl.blastmaze.appwarp.WarpListener;

import java.util.Locale;
import java.util.Random;

public class StreamController implements Screen, WarpListener {

    private final String[] tryingToConnect = {"Connecting","to AppWarp"};
    private final String[] waitForOtherUser = {"Waiting for","other user"};
    private final String[] errorInConnection = {"Error in","Connection", "Go Back"};

    private final String[] game_win = {"Congrats You Win!", "Enemy Defeated"};
    private final String[] game_loose = {"Oops You Loose!","Target Achieved","By Enemy"};
    private final String[] enemy_left = {"Congrats You Win!", "Enemy Left the Game"};

    private String[] msg = tryingToConnect;

    Texture img;

    //todo: dump in touch controls

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
        WarpController.getInstance().startApp(getRandomHexString(10));
        WarpController.getInstance().setListener(this);
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

    public void update(){

        WarpController.getInstance().handleLeave();
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

    @Override
    public void onError (String message) {
        this.msg = errorInConnection;
        update();
    }

    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }

    @Override
    public void onGameStarted (String message) {
        /*Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                MazeGame.setScreen(new MultiplayerGameScreen(game, StartMultiplayerScreen.this));
            }
        });*/

    }

    @Override
    public void onGameFinished (int code, boolean isRemote) {
        if(code==WarpController.GAME_WIN){
            this.msg = game_loose;
        }else if(code==WarpController.GAME_LOOSE){
            this.msg = game_win;
        }else if(code==WarpController.ENEMY_LEFT){
            this.msg = enemy_left;
        }
        update();
        //MazeGame.setScreen(this);
    }

    @Override
    public void onGameUpdateReceived (String message) {

    }

    @Override
    public void onWaitingStarted(String message) {
        this.msg = waitForOtherUser;
        update();
    }

    //this sends the data from phone to phone
    //todo: enable this and update location.
    /*private void sendLocation(float x, float y, float width, float height){
        counter++;
        try {
            JSONObject data = new JSONObject();
            data.put("x", x);
            data.put("y", y);
            data.put("width", width);
            data.put("height", height);
            data.put("bomb", bomb);
            //etc

            if(counter%10==0){
                counter=0;
                WarpController.getInstance().sendGameUpdate(data.toString());
            }
        } catch (Exception e) {
            // exception in sendLocation
        }
    }*/
}
