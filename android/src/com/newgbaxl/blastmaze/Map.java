package com.newgbaxl.blastmaze;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import android.icu.util.TimeUnit;
import android.util.TimeUtils;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.newgbaxl.blastmaze.Objects.Car;
import com.newgbaxl.blastmaze.Objects.Counter;
import com.newgbaxl.blastmaze.Objects.GameObject;
import com.newgbaxl.blastmaze.Objects.UserCar;
import com.newgbaxl.blastmaze.databinding.FragmentGameViewBinding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Toast;

import java.sql.Time;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Map extends View {
    private Paint wallPaint, wallPaintGhost;
    private Paint playerTestPaint, exitPaint;
    private Paint carPaint = new Paint();

    private static final float WALL_THICKNESS = 4;
    private Cell exitSpace;
    public static boolean inv = false;
    public static FragmentGameViewBinding UIBinding;
    Dpad dpad;
    public Counter bombsCounter;
    public Counter blocksCounter;

    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture;

    //Timing
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawns = 1f;
    private float enemySpawnTimer = 20;

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

    List<GameObject> objects;

    public Map(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cells = new Cell[xWidth][yHeight];
        carPaint.setColor(Color.GRAY);
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);
        wallPaintGhost = new Paint();
        wallPaintGhost.setColor(Color.BLUE);
        wallPaintGhost.setStrokeWidth(WALL_THICKNESS);
        createMaze();
        playerTestPaint = new Paint();
        playerTestPaint.setColor(Color.RED);
        exitPaint = new Paint();
        exitPaint.setColor(Color.MAGENTA);

        //playerTest = cells[0][0];
        exitSpace = cells[xWidth - 1][yHeight - 1];
        dpad = new Dpad();

        objects = new ArrayList<>();
        player = new UserCar(this, 32, 32, carPaint, (byte)10, (byte)10);
        objects.add(player);
        objects.add(new Car(this, 352, 352, carPaint, (byte)10, (byte)10));

        //prepareHUD();
    }

    private void createMaze() {
        for (int x = 0; x < xWidth; ++x) {
            for (int y = 0; y < yHeight; ++y) {
                cells[x][y] = new Cell(x, y);
            }
        }

        for (int x = 0; x < xWidth; ++x) {
            cells[x][0].buildWall((byte)0, -1); //sets first nWall to -1
            cells[x][yHeight - 1].buildWall((byte)2, -1); //sets last sWall to -1
        }
        for (int y = 0; y < yHeight; ++y) {
            cells[0][y].buildWall((byte)3, -1); //sets first wWall to -1
            cells[xWidth - 1][y].buildWall((byte)1, -1); //sets last eWall to -1
        }
    }

    private void UpdateObjects()
    {
        for (GameObject o : objects) o.Update();
    }

    //libGDX uses render(float deltaTime) instead
    @Override
    protected void onDraw(Canvas canvas)
    {
        prepareHUD();
        UpdateObjects();

        canvas.drawColor(Color.GREEN);
        int width = getWidth();
        int height = getHeight();

        if (width / (height + 1) < xWidth / yHeight)
            cellSize = width / (xWidth + 1);
        else
            cellSize = height / (yHeight + 1);

        hMargin = (width - xWidth * cellSize) / 2;
        vMargin = (height - yHeight * cellSize) / 2;
        canvas.translate(hMargin, vMargin);

        //north walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine( //if -1 or >0 wall, if 0 show ghost
                        i * cellSize, j * cellSize, (i + 1) * cellSize,
                        j * cellSize, (cells[i][j].nWall != 0) ? wallPaint : wallPaintGhost);
            }
        }
        //south walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine(
                        i * cellSize, (j+1) * cellSize, (i + 1) * cellSize,
                        (j+1) * cellSize, (cells[i][j].sWall != 0) ? wallPaint : wallPaintGhost);
            }
        }
        //east walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine(
                        (i+1) * cellSize, j * cellSize, (i+1) * cellSize,
                        (j + 1) * cellSize, (cells[i][j].eWall != 0) ? wallPaint : wallPaintGhost);
            }
        }
        //west walls
        for (int i = 0; i < xWidth; ++i) {
            for (int j = 0; j < yHeight; ++j) {
                canvas.drawLine(
                        i * cellSize, j * cellSize, i * cellSize,
                        (j + 1) * cellSize, (cells[i][j].wWall != 0) ? wallPaint : wallPaintGhost);
            }
        }

        for (GameObject o : objects) o.Draw(canvas);

        //Wait to cap framerate, then signal to redraw
        try {Thread.sleep(5);}
        catch (InterruptedException ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();}

        invalidate();
    }

    void breakWall(int x, int y, byte wallId, int str) {
        //if cell method return true, destroy adj wall on adj cell
        if (cells[x][y].breakWall(wallId, str)) {
            if (wallId == 0)
                cells[x][y - 1].breakWall((byte)2, str);
            else if (wallId == 1)
                cells[x + 1][y].breakWall((byte)3, str);
            else if (wallId == 2)
                cells[x][y + 1].breakWall((byte)0, str);
            else if (wallId == 3)
                cells[x - 1][y].breakWall((byte)1, str);
            invalidate(); //inefficient, fix later
        }
    }

    void buildWall(int x, int y, byte wallId, int str) {
        //if cell method return true, build adj wall on adj cell
        if (cells[x][y].buildWall(wallId, str)) {
            if (wallId == 0)
                cells[x][y - 1].buildWall((byte)2, str);
            else if (wallId == 1)
                cells[x + 1][y].buildWall((byte)3, str);
            else if (wallId == 2)
                cells[x][y + 1].buildWall((byte)0, str);
            else if (wallId == 3)
                cells[x - 1][y].buildWall((byte)1, str);
            invalidate(); //inefficient, fix later
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        // Check if this event if from a D-pad and process accordingly.
        if (Dpad.isDpadDevice(event)) {
            int press = dpad.getDirectionPressed(event);
            player.moveTo((byte) press);
            invalidate();
            return true;
        }

        // Check if this event is from a joystick movement and process accordingly.

        return super.onGenericMotionEvent(event);
    }

    private void processJoystickInput(MotionEvent event, int historyPos) {
        InputDevice inputDevice = event.getDevice();
    }

    private void prepareHUD(){
        //Create a Bitmap font from our font file
        /*FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontPerameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color (1,1,1,0.3f);
        fontParameter.borderColor = new Color (0,0,0,0.3f);

        font = fontGenerator.generateFont(fontParameter);*/

        //scale the font to fit world
        font.getData().setScale(0.08f);

        //calculate hud margins, etc

        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCenterX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }

    private void updateAndRenderHUD(){
        //render top row labels
        font.draw(batch, "Blocks", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Bombs", hudCenterX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Power", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //"Timer"

        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", player.blocks),
                hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%06d", player.bombs),
                hudCenterX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%06d", player.power),
                hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
        //Timer
    }
}

//Maze array notes
//if edge, set to -1
//if no wall, set to 0
//if wall has strength n, set to n
//if exit set to -2