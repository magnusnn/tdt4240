package com.example.pong;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.input.TouchListener;

/**
 * Created by Magnus on 07.02.2016.
 */

public class LaunchGame extends State implements TouchListener{

    private Font font;

    public LaunchGame(){
        font = new Font(100, 100, 100, 60, Typeface.SANS_SERIF, Typeface.BOLD);
        font.setTextAlign(Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("Første mann til to poeng!!", canvas.getWidth()/2, canvas.getHeight()/2, font);
        canvas.drawText("Trykk for å starte spillet!", canvas.getWidth()/2, canvas.getHeight()/2, font);

    }

    @Override
    public boolean onTouchUp(MotionEvent event) {

        getGame().popState();
        getGame().pushState(new GameState());
        return false;
    }


}
