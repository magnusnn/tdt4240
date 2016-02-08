package com.example.pong;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.input.TouchListener;

/**
 * Created by Magnus on 07.02.2016.
 */

public class GameState extends State implements TouchListener{

    private int canvasHeight, canvasWidth;
    private Sprite paddle1, paddle2, ball;
    private Image paddleSprite1, paddleSprite2, ballImage;
    private int paddle1count, paddle2count;
    private Font font;
    private Random random, random2;

    public GameState() {
        this.random = new Random();
        this.random2 = new Random();

        this.font = new Font(255, 255, 20, 50, Typeface.SERIF, Typeface.NORMAL);

        this.paddle1count = 0;
        this.paddle2count = 0;
        this.paddleSprite1 = new Image(R.drawable.paddle );
        this.paddleSprite2 = new Image(R.drawable.paddle);
        this.ballImage = new Image(R.drawable.ball);

        this.paddle1 = new Sprite(paddleSprite1);
        this.paddle2 = new Sprite(paddleSprite2);
        this.ball = new Sprite(ballImage);

        this.ball.setPosition(450, 500);
        this.paddle1.setPosition(200, 80);
        this.paddle2.setPosition(200, 1010);
        this.ball.setSpeed(300, 270);
    }



    @Override
    public boolean onTouchMove(MotionEvent event) {

        if(event.getY()< canvasHeight/2){
            paddle1.setPosition(event.getX(), paddle1.getY());
            return true;
        }
        if(event.getY()> canvasHeight/2){
            paddle2.setPosition(event.getX(), paddle2.getY());
            return true;
        }

        return false;
    }



    @Override
    public void update(float dt) {
        ball.update(dt);
        paddle1.update(dt);
        paddle2.update(dt);

        checkWinner();
        checkWall();
        checkPaddle();
        checkGoal();
    }

    @Override
    public void draw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        canvas.drawColor(Color.DKGRAY);

        drawScore(canvas);

        ball.draw(canvas);
        paddle1.draw(canvas);
        paddle2.draw(canvas);

    }

    public void checkWinner() {
        if(paddle1count==2){
            getGame().popState();
            getGame().pushState(new GameOver(1));
        }
        if(paddle2count==2){
            getGame().popState();
            getGame().pushState(new GameOver(2));
        }
    }

    public void checkWall(){
        if(ball.getX()>(canvasWidth-ballImage.getWidth()) || ball.getX()<0)
        {
            ball.setSpeed(-ball.getSpeed().getX(), ball.getSpeed().getY());

        }
        if(ball.getY()>(canvasHeight-ballImage.getHeight()) || ball.getY()<0)
        {
            ball.setSpeed(ball.getSpeed().getX(), -ball.getSpeed().getY());
        }
    }

    public void checkPaddle(){
        if(ball.collides(paddle1)){
            ball.setSpeed(ball.getSpeed().getX(), -ball.getSpeed().getY());
        }
        if(ball.collides(paddle2)){
            ball.setSpeed(ball.getSpeed().getX(), -ball.getSpeed().getY());
        }
    }

    public void checkGoal(){
        if(ball.getY()<paddle1.getY()){
            ball.setPosition(canvasHeight/2, canvasWidth/2);
            paddle1count++;
        }
        if(ball.getY()>paddle2.getY()){
            ball.setPosition(canvasHeight/2, canvasWidth/2);
            paddle2count++;
        }
    }


    public void drawScore(Canvas canvas){
        canvas.drawText("" + paddle2count, 40, 480, font);
        canvas.drawText("" + paddle1count, 40, 560, font);
        canvas.drawRect(0, 505, 700, 510, sheep.graphics.Color.WHITE);
    }

}