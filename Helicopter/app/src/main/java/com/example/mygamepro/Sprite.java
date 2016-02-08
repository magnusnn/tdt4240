package com.example.mygamepro;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;

import java.util.Random;
import java.lang.Object;

import sheep.graphics.Color;
import sheep.math.Vector2;

/**
 * Created by Magnus on 06.02.2016.
 */
public class Sprite {
    private static final float BMP_ROWS = 1;
    private static final float BMP_COLUMNS = 4;
    private float x = 0;
    private float y = 0;
    private int MAX_SPEED = 5;
    private float xSpeed = 5;
    private GameView gameView;
    private Bitmap bmp;
    private float currentFrame = 0;
    private float width;
    private float height;
    private float ySpeed = 5;
    private Rect src;
    private Rect dst;
    private boolean directionLeft = true;
    private boolean directionDown = true;

    public Sprite(GameView gameView, Bitmap bmp){
        this.gameView = gameView;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random(System.currentTimeMillis());

        xSpeed = java.lang.Math.abs(rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED);
        ySpeed = (rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED);
        x = java.lang.Math.abs(rnd.nextInt(700 - (int)width));
        y = java.lang.Math.abs(rnd.nextInt(1000 - (int)height));

//        Random rnd = new Random();
//        this.x = rnd.nextInt(gameView.getWidth() - (int)width);
//        this.y = rnd.nextInt(gameView.getHeight() - (int)height);
//        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
//        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        flipHelicopter();
    }


    private void update() {
        if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
            flipHelicopter();
        }
        x += xSpeed;
        if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }
        y += ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas) {

        update();
        float srcX = currentFrame * width;
        float srcY = 1;
        src = new Rect((int)srcX, (int)srcY, (int)(srcX + width), (int)(srcY + height));
        dst = new Rect((int)x, (int)y, (int)(x + width), (int) (y + height));

        canvas.drawBitmap(bmp, src, dst, null);

    }


    public void flipHelicopter(){
        Matrix mirrorMatrix = new Matrix();
        mirrorMatrix.preScale(-1, 1);
        Bitmap turnMap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mirrorMatrix, false);
        turnMap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        bmp = new BitmapDrawable(turnMap).getBitmap();

        if (directionLeft == false){
            directionLeft = true;
        }
        else{
            directionLeft = false;
        }
    }


    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public float getxSpeed(){
        return this.xSpeed;
    }
    public float getySpeed(){
        return this.ySpeed;
    }

    public void setxSpeed(float xSpeed){
        this.xSpeed = xSpeed;
    }
    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }


    public void setDirection(float xPosition, float yPosition){
        float tempX = xPosition - getX();
        float tempY = yPosition - getY();
        xSpeed = 0;
        ySpeed = 0;
        if (tempX != 0) {
            setxSpeed(tempX / 100);
        }
        if (tempY != 0) {
            setySpeed(tempY / 100);
        }

        if (getX() + width/2 > xPosition && !directionLeft){
            flipHelicopter();
        }
        if (getY() > yPosition && directionDown) {
//            setySpeed(-getySpeed());
            directionDown = false;
        }

        if ((getX() + width/2 <= xPosition) && (directionLeft)){
//            setxSpeed(-getxSpeed());
            flipHelicopter();

        }
        if (getY() <= yPosition&& !directionDown){
//            setySpeed(-getySpeed());
            directionDown = true;
        }
    }


}