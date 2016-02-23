package com.example.pong;

/**
 * Created by Magnus on 21.02.2016.
 */
public class ScoreCounter {

    private int scorePlayer1, scorePlayer2;

    public ScoreCounter(){
        this.scorePlayer1 = 0;
        this.scorePlayer2 = 0;
    }

    public void goalPlayer1(){
        scorePlayer1++;
    }
    public void goalPlayer2(){
        scorePlayer2++;
    }
    public int getScorePlayer1(){
        return scorePlayer1;
    }
    public int getScorePlayer2() {
        return scorePlayer2;
    }
}
