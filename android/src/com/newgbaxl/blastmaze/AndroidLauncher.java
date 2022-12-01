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
import com.newgbaxl.blastmaze.MazeGame;

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

		if (carSkin == NULL || carSkin == -1) {//if no pushed params (not sure exactly how this works)
			initialize(new MazeGame(-1, special, scenario), config);
			Log.d("test", "wrong thing");
		}
		else {
			initialize(new MazeGame(carSkin, special, scenario), config);
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