package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.input.TouchListener;

/**
 * Created by Magnus on 07.02.2016.
 */

public class GameOver extends State implements TouchListener{

    private Font font, font2;
    private int winner;
    public GameOver(int winner){
        this.winner = winner;
        font = new Font(88, 88, 88, 50, Typeface.SERIF, Typeface.BOLD);
        font2 = new Font(100, 100, 100, 30, Typeface.SERIF, Typeface.NORMAL);
        font.setTextAlign(Align.CENTER);
        font2.setTextAlign(Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawText("Vinneren ble", canvas.getWidth()/2, 200, font);
        canvas.drawText("spiller " + winner + "!", canvas.getWidth()/2, 300, font);
        canvas.drawText("Trykk for å begynne nytt spill!", canvas.getWidth()/2, 450, font2);
    }

    @Override
    public boolean onTouchUp(MotionEvent event) {

        getGame().popState();
        getGame().pushState(new GameState());
        return true;
    }


}