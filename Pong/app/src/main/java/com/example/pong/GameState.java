package com.example.pong;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
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
    private Image paddleImage1, paddleImage2, ballImage;
    private int paddle1count, paddle2count;
    private Font font;

    public GameState() {

        font = new Font(0, 55, 20, 50, Typeface.SERIF, Typeface.NORMAL);

        paddle1count = 0;
        paddle2count = 0;
        paddleImage1 = new Image(R.drawable.pongpaddle_white );
        paddleImage2 = new Image(R.drawable.pongpaddle_white);
        ballImage = new Image(R.drawable.ball_white);

        paddle1 = new Sprite(paddleImage1);
        paddle2 = new Sprite(paddleImage2);
        ball = new Sprite(ballImage);

        ball.setPosition(250, 200);
        paddle1.setPosition(200, 80);
        paddle2.setPosition(200, 610);
        ball.setSpeed(250, 170);
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

        //Sjekke om vinner

        if(paddle1count==2){
            getGame().popState();
            getGame().pushState(new GameOver(1));
        }
        if(paddle2count==2){
            getGame().popState();
            getGame().pushState(new GameOver(2));
        }

        //Sjekke veggkollisjon

        if(ball.getX()>(canvasWidth-ballImage.getWidth()) || ball.getX()<0)
        {
            ball.setSpeed(-ball.getSpeed().getX(), ball.getSpeed().getY());

        }
        if(ball.getY()>(canvasHeight-ballImage.getHeight()) || ball.getY()<0)
        {
            ball.setSpeed(ball.getSpeed().getX(), -ball.getSpeed().getY());
        }

        //Sjekke kollisjon med paddelen

        if(ball.collides(paddle1)){
            ball.setSpeed(ball.getSpeed().getX(), -ball.getSpeed().getY());
        }
        if(ball.collides(paddle2)){
            ball.setSpeed(ball.getSpeed().getX(), -ball.getSpeed().getY());
        }


        //Se om ballen kommer bak paddelen

        if(ball.getY()<paddle1.getY()){
            ball.setPosition(canvasHeight/2, canvasWidth/2);
            paddle1count++;
            Log.w("GameState", "One point for paddle2: " + paddle2count);
        }
        if(ball.getY()>paddle2.getY()){
            ball.setPosition(canvasHeight/2, canvasWidth/2);
            paddle2count++;
            Log.w("GameState", "One point for paddle1: " + paddle1count);
        }



    }

    @Override
    public void draw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        canvas.drawColor(Color.BLUE);

        canvas.drawText("" + paddle2count, 40, 310, font);
        canvas.drawText("" + paddle1count, 390, 400, font);
        canvas.drawRect(0, 338, 480, 342, sheep.graphics.Color.BLACK);

        ball.draw(canvas);
        paddle1.draw(canvas);
        paddle2.draw(canvas);

    }

}