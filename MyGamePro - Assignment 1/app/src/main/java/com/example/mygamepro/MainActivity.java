package com.example.mygamepro;

import sheep.game.Game;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    /* Called when the activity is created */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Create the game */
        Game game = new Game(this, null);
        /* Push the game state */
        game.pushState(new TitleScreen(this));


//        setContentView(R.layout.activity_main);
        setContentView(game);
    }
}
