package com.example.mygamepro;

import sheep.game.Sprite;
import sheep.graphics.Color;
import android.graphics.Rect;
import android.graphics.Canvas;


/**
 * Created by Magnus on 01.02.2016.
 */
public class Wall extends Sprite {

    private Rect rectangle;
    private Color color;


   public Wall(int left, int top, int right, int bottom, Color color){
        rectangle = new Rect(left, top, right, bottom);
       this.color = color;
   }


   public void draw(Canvas canvas){
       canvas.drawRect(rectangle, color);
   }

}
