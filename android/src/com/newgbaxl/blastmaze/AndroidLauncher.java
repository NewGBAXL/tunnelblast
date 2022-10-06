package com.newgbaxl.blastmaze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.newgbaxl.blastmaze.BlastMazeGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new BlastMazeGame(), config); //this loads the "game" screen in core

		//create splash screen & credits

		//loads MainActivity after delay
		/*Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				finish();
				Intent i3 = new Intent(AndroidLauncher.this, MainActivity.class);
				//startActivity(i3);
			}
		}, 2500);*/
	}

	public void goToAnActivity(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}