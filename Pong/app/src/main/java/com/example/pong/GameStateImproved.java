package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.input.TouchListener;

/**
 * Created by Magnus on 22.02.2016.
 */
public class GameStateImproved extends State implements TouchListener{

    private int canvasHeight, canvasWidth;
    private Sprite paddle1, paddle2, ball;
    private Image paddleSprite1, paddleSprite2, ballImage;
    private static int paddle1count, paddle2count;
    private Font font, smallFont;
    private static int runtimeCounter = 0;

    //singleton pattern
    private static GameStateImproved instance = null;

    //observable added due to task 3
    private ScoreCounter score;
    private ArrayList<ScoreCounter> observableCollection;

    protected GameStateImproved() {
        System.out.println("new game");
        score = new ScoreCounter();
        observableCollection = new ArrayList<ScoreCounter>();
        observableCollection.add(score);

        this.font = new Font(255, 255, 20, 50, Typeface.SERIF, Typeface.NORMAL);
        this.smallFont = new Font(255, 255, 20, 50, Typeface.SERIF, 5);

        this.paddle1count = 0;
        this.paddle2count = 0;
        this.paddleSprite1 = new Image(R.drawable.paddle );
        this.paddleSprite2 = new Image(R.drawable.paddle);
        this.ballImage = new Image(R.drawable.ball);

        this.paddle1 = new Sprite(paddleSprite1);
        this.paddle2 = new Sprite(paddleSprite2);
        this.ball = new Sprite(ballImage);


        this.paddle1.setPosition(300, 100);
        this.paddle2.setPosition(300, 500);
        this.ball.setPosition(200, 200);
        this.ball.setSpeed(300, 270);

    }

    //singleton pattern
    public static GameStateImproved getInstance(){
        if(instance == null) {
            instance = new GameStateImproved();
        }
        runtimeCounter++;
        return instance;

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
    public void draw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        canvas.drawColor(Color.DKGRAY);


        drawScore(canvas);


        paddle1.setPosition(paddle1.getX(), canvasHeight / 6);
        paddle2.setPosition(paddle2.getX(), canvasHeight / 6 * 5);



        paddle1.draw(canvas);
        paddle2.draw(canvas);

        ball.draw(canvas);
    }

    @Override
    public void update(float dt) {

        paddle1.update(dt);
        paddle2.update(dt);


        checkWall();
        checkPaddle();
        checkGoal();
        checkWinner();

        ball.update(dt);
    }


    public void checkWinner() {
        if(score.getScorePlayer1() == 4) {
            getGame().popState();
            getGame().pushState(new GameOver(1));
            instance = null;
        }
        if(score.getScorePlayer2() == 4) {
            getGame().popState();
            getGame().pushState(new GameOver(2));
            instance = null;
        }
    }

    public void checkWall(){
        if(ball.getX()>(canvasWidth-ballImage.getWidth()) || ball.getX()<=0)
        {
            ball.setSpeed(-ball.getSpeed().getX(), ball.getSpeed().getY());
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
        if(ball.getY()<=paddle1.getY()){
            ball.setPosition(canvasHeight/2, canvasWidth/2);
            System.out.println("point player 1");
            notifyBallObservers(1);
        }
        if(ball.getY()>paddle2.getY()){
            ball.setPosition(canvasHeight/2, canvasWidth/2);
            notifyBallObservers(2);
            System.out.println("point player 2");
        }
    }


    public void drawScore(Canvas canvas){
        canvas.drawText("" + score.getScorePlayer1(), 20, (canvasHeight/6*3) -10, smallFont);
        canvas.drawText("" + score.getScorePlayer2(), 20, (canvasHeight/6*3) +50, font);
        canvas.drawRect(0, canvasHeight/6*3, canvasWidth, (canvasHeight/6*3) +5, sheep.graphics.Color.WHITE);
    }


    public void notifyBallObservers(int goalScoredByPlayer){
        if (goalScoredByPlayer == 1 && runtimeCounter == 1){
                score.goalPlayer1();
            }
        if (goalScoredByPlayer == 2){
            score.goalPlayer2();
        }
        //point on restart bug
        runtimeCounter = 1;
    }

}
