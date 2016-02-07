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
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
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
        System.out.println("GAMEVIEW: " + gameView.getWidth());

//        System.out.println(getX());
    }


    public void flipHelicopter(){
        Matrix mirrorMatrix = new Matrix();
        mirrorMatrix.preScale(-1, 1);
        Bitmap turnMap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mirrorMatrix, false);
        turnMap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        bmp = new BitmapDrawable(turnMap).getBitmap();
        System.out.println(directionLeft);
        if (directionLeft == false){
            directionLeft = true;
        }
        else{
            directionLeft = false;
        }
        System.out.println(directionLeft);
        System.out.println("-----------------------");
        System.out.println("HEIGHT: " + gameView.getHeight());
        System.out.println("WIDTH : " + gameView.getWidth());
        System.out.println("-----------------------");
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

//    public void setRate(float rateX, float rateY){
//        this.
//    }

    public void setDirection(float xPosition, float yPosition){
        float tempX = xPosition - getX();
        float tempY = yPosition - getY();
        xSpeed = 0;
        ySpeed = 0;
        if (tempX != 0) {
//            if (tempX > gameView.getWidth()/2 || tempX < -gameView.getWidth()/2){
//                setxSpeed(tempX / 100);
//            } else if (tempX <= gameView.getWidth()/2 || tempX >=-gameView.getWidth()/2 ){
//                setxSpeed(tempX / 10);
//            }
            setxSpeed(tempX / 100);
        }
        if (tempY != 0) {
//            if (tempY > gameView.getHeight()/2 || tempY < -gameView.getHeight()/2){
//                setySpeed(tempY / 100);
//            } else if (tempY <= gameView.getHeight()/2 || tempY >=-gameView.getHeight()/2 ){
//                setySpeed(tempY / 10);
//            }
            setySpeed(tempY / 100);
        }
        System.out.println("tempX: " + tempX);
        System.out.println("tempY: " + tempY);

        int ratio = 1;
//        System.out.println(getX());
//        System.out.println(getAnimationRow());
        if (getX() + width/2 > xPosition && !directionLeft){
            flipHelicopter();
//            ratio = getRatio(get)
//            setxSpeed(-getxSpeed());
//            setxSpeed(-getxSpeed());

//            setySpeed(3/4*getySpeed());
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



//    public void moveForward() {
//        ySpeed += 0.01 * Math.sin(Math.toRadians(targetAngle));
//        xSpeed += 0.01 * Math.cos(Math.toRadians(targetAngle));
//        double currentSpeed = Math.sqrt(xTempSpeed * xTempSpeed + yTempSpeed * yTempSpeed);
//        if (currentSpeed > maxSpeed) {
//            //the resulting speed is allways <= maxspeed (normed to that)
//            ySpeed *= maxSpeed/currentSpeed;
//            xSpeed *= maxSpeed/currentSpeed;
//        }

//
//    public int getRatio(int getXY, int xyPosition){
//
//    }

}