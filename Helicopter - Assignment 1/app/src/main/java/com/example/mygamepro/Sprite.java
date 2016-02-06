package com.example.mygamepro;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;

import java.util.Random;

import sheep.graphics.Color;

/**
 * Created by Magnus on 06.02.2016.
 */
public class Sprite {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 1;
    private static final int BMP_COLUMNS = 4;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int ySpeed = 5;
    private Rect src;
    private Rect dst;

    public Sprite(GameView gameView, Bitmap bmp){
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;
        flipHelicopter();
    }


    private void update() {
        if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
            flipHelicopter();
        }
        x = x + xSpeed;
        if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas) {

        update();
        int srcX = currentFrame * width;
        int srcY = 1;
        src = new Rect(srcX, srcY, (srcX + width), (srcY + height));
        dst = new Rect(x, y, (x + width), (y + height));

        canvas.drawBitmap(bmp, src, dst, null);
    }


    public void flipHelicopter(){
        Matrix mirrorMatrix = new Matrix();
        mirrorMatrix.preScale(-1, 1);
        Bitmap turnMap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mirrorMatrix, false);
        turnMap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        bmp = new BitmapDrawable(turnMap).getBitmap();
    }
}