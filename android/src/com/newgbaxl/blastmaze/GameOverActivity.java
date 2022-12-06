package com.newgbaxl.blastmaze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        money = MazeScreen2d.coinsCollected;

        //Proof of concept stuff since I don't know exactly what you want
        if (MazeScreen2d.result > 0){
            ((TextView)findViewById(R.id.gameOverText)).setText("You Win");
            if (MazeScreen2d.currentLvlID < 19 && GlobalVars.globalRanks[MazeScreen2d.currentLvlID + 1] == 0)
                GlobalVars.globalRanks[MazeScreen2d.currentLvlID + 1] = 1;
        }
        if (MazeScreen2d.result == 0) ((TextView)findViewById(R.id.gameOverText)).setText("You Died");

        ((TextView)findViewById(R.id.moneyView)).setText("You received " + money); //and add money sprite
        GlobalVars.globalMoney += money;

        if (MazeScreen2d.currentLvlID < 20 && MazeScreen2d.currentLvlID >= 0)
            GlobalVars.globalRanks[MazeScreen2d.currentLvlID] =
                    Math.max(GlobalVars.globalRanks[MazeScreen2d.currentLvlID], MazeScreen2d.result);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toMainMenu();
            }
        });
    }

    void toMainMenu(){
        //called on Go to Menu
        startActivity(new Intent(GameOverActivity.this, FirstFragment.class));
    }
}