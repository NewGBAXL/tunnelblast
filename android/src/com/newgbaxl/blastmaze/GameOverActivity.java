package com.newgbaxl.blastmaze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameOverActivity extends AppCompatActivity {

    int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        money = getIntent().getIntExtra("Money",0);
    }
}