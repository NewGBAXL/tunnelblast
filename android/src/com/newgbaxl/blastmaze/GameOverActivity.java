package com.newgbaxl.blastmaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        money = getIntent().getIntExtra("Money",0);

        //Proof of concept stuff since I don't know exactly what you want
        if (MazeScreen2d.win) ((TextView)findViewById(R.id.gameOverText)).setText("You Win");
        if (MazeScreen2d.lose) ((TextView)findViewById(R.id.gameOverText)).setText("You Died");
    }
}