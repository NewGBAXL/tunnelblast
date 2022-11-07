package com.newgbaxl.blastmaze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.newgbaxl.blastmaze.MazeGame;

public class AndroidLauncher extends AndroidApplication
{
	//todo: store global variables here
	//NewGBAXL

	public boolean splash = true;

	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		//comment this out run app normally
		initialize(new MazeGame(), config);

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
		initialize(new MazeGame(mode), config);
	}
}