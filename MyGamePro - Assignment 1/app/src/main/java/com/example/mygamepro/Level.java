package com.example.mygamepro;

/**
 * Created by Magnus on 01.02.2016.
 */



import java.util.ArrayList;

import android.graphics.Canvas;
import sheep.graphics.*;

public class Level {

    private ArrayList<Wall> walls;
    private int width;
    private int height;

    public Level() {
        walls = new ArrayList<Wall>();
        walls.add(new Wall(0, 0, 999, 100, Color.BLUE));
        walls.add(new Wall(0, 100, 40, 950, Color.BLUE));
        walls.add(new Wall(0, 950, 999, 999, Color.BLUE));
        walls.add(new Wall(40, 630, 300, 690, Color.BLUE));
        walls.add(new Wall(100, 150, 400, 555, Color.BLUE));
        walls.add(new Wall(200, 850, 275, 950, Color.BLUE));
        walls.add(new Wall(355, 555, 400, 950, Color.BLUE));
        walls.add(new Wall(475, 100, 675, 325, Color.BLUE));
        walls.add(new Wall(630, 400, 675, 950, Color.BLUE));
        walls.add(new Wall(675, 100, 930, 200, Color.BLUE));
        walls.add(new Wall(930, 100, 999, 950, Color.BLUE));
        width = 1000;
        height = 1000;
    }

    public void draw(Canvas canvas) {
        for (Wall wall : walls) {
            wall.draw(canvas);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
