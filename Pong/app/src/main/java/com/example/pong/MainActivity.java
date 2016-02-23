package com.example.pong;

import android.app.Activity;
import android.os.Bundle;

import sheep.game.Game;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Game game = new Game(this, null);

        //singleton pattern
        game.pushState(GameStateImproved.getInstance());

        setContentView(game);
    }
}
