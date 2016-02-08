package com.example.mygamepro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import sheep.graphics.Font;


/**
 * Created by Magnus on 06.02.2016.
 */
public class GameView extends SurfaceView {


    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite sprite;


    public GameView (Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry){
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e){
                        System.out.println("GameView error: " + e.toString());
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

            });

        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.heli1_west_animated_trans);
        sprite = new Sprite(this, bmp);
    }

    @Override
    protected void onDraw (Canvas canvas){
        canvas.drawColor(Color.WHITE);
        sprite.onDraw(canvas);
        Font font = new Font(0, 55, 20, 30, Typeface.SERIF, Typeface.NORMAL);
        canvas.drawText("Helikopter er på følgende koordinater:" , 30, 30, font);
        canvas.drawText("x: " + sprite.getX() , 30, 60, font);
        canvas.drawText("y: " + sprite.getY() , 30, 90, font);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        System.out.println("X: " + event.getX());
        System.out.println("Y: " + event.getY());
        sprite.setDirection((int)event.getX(),(int)event.getY());
        return super.onTouchEvent(event);
    }

}