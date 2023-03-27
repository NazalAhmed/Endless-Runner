package com.example.endlessrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvPoints = findViewById(R.id.scoretextview);

        int score = getIntent().getExtras().getInt("score");
        tvPoints.setText("Score: " + score);
        sharedPreferences = getSharedPreferences("my_pref", 0);
    }

    public void restartGame(View view) {
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }

    public void exit(View view) {
        finish();
    }
}