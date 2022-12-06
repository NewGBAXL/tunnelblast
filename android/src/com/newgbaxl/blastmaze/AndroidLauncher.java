package com.newgbaxl.blastmaze;

import static java.sql.Types.NULL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.newgbaxl.blastmaze.MazeGame;
import com.newgbaxl.blastmaze.objects.EnemyCar;

public class AndroidLauncher extends AndroidApplication
{
	public boolean splash = true;
	int money = 0;

	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		money = getIntent().getIntExtra("Money",0);
		int carSkin = getIntent().getIntExtra("Car", -1); //default - quick start
		int special = getIntent().getIntExtra("Weapon", 0);
		int scenario = getIntent().getIntExtra("Level", -1);
		int vsMode = getIntent().getIntExtra("VsMode", 0);
		int enemies = getIntent().getIntExtra("Enemies",-1);
		int bombs = getIntent().getIntExtra("Bombs",-1);
		int walls = getIntent().getIntExtra("Walls",-1);
		int timer = getIntent().getIntExtra("Timer",-1);
		int mode = getIntent().getIntExtra("Mode",1);

		if (bombs != -1) {
			initialize(new MazeGame(carSkin, special, new Scenario() {
				@Override
				public void OnStart(MazeScreen2d scene) {
					for (int i = 0; i < enemies; i++)
					{
						int y = 5 + enemies / 2 + i;
						int x = 10 + enemies / 4 + Math.abs(5 - i);
						scene.enemies.add(new EnemyCar(x, y, Color.RED));
					}
					for (int i = 0; i < scene.enemies.size(); i++) scene.stage.addActor(scene.enemies.get(i));

					scene.user.bombs = bombs;
					scene.user.blocks = walls;
					scene.user.timer = timer * 60;
				}

				@Override
				public void Update(MazeScreen2d scene) {

				}

				@Override
				public boolean CheckForWin(MazeScreen2d scene) {
					return false;
				}

				@Override
				public int getRankResult(MazeScreen2d scene) {
					return 0;
				}
			}, vsMode));
		} else if (carSkin == NULL || carSkin == -1)
		{
			//if no pushed params (not sure exactly how this works)
			initialize(new MazeGame(-1, special, scenario, vsMode), config);
			Log.d("test", "wrong thing");
		}
		else
		{
			initialize(new MazeGame(carSkin, special, scenario, vsMode), config);
			Log.d("test", "right thing");
		}

		//the mazeGame will store the params of each scenario


		//early notes (ignore)
		//initialize(new BlastMazeGame(), config); //this loads the "game" screen in core
		//create splash screen & credits
		//loads MainActivity after delay

		//startGame(savedInstanceState);
		/*if (splash = true)
		{
			splash = false;
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					finish();
					Intent i3 = new Intent(AndroidLauncher.this, MainActivity.class);
					//startActivity(i3);
				}
			}, 2500);
		}
		else
			startGame(savedInstanceState);*/
	}

	public void goToAnActivity(View view)
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void startGame(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		int mode = 0; //0 - 24
		//initialize(new MazeGame(mode), config);
	}

	@Override
	protected void onDestroy() {
		Intent intent = new Intent(this, GameOverActivity.class);
		//todo: push game stats to GameOverActivity
		intent.putExtra("Money",money);
		startActivity(intent);
		super.onDestroy();
	}
}