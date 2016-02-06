package com.example.mygamepro;

/**
 * Created by Magnus on 01.02.2016.
 */

        import android.app.Activity;
        import android.app.ActionBar;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.util.DisplayMetrics;

        import android.view.Window;
        import android.widget.Toolbar;

        import sheep.game.Sprite;
        import sheep.game.State;
        import sheep.graphics.Image;



public class TitleScreen extends State {
    private Image heliImage = new Image(R.drawable.heli1_east_trans);
    private Image aImage = new Image(R.drawable.icon);
    private Image wallVerImage = new Image(R.drawable.wall_vertical);
    private Image backgroundImage = new Image(R.drawable.background);
    private Sprite aSprite;
    private Sprite westWall;
    private Sprite eastWall;
    private Sprite topWall;
    private Sprite bottomWall;
    private Sprite backSprite1;
    private Sprite backSprite2;
    private Sprite backSprite3;
    private Sprite heliSprite;
    private Canvas rect;
    private DisplayMetrics metrics = new DisplayMetrics();
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int bug = 0;
    private int TitleBarHeight;
//    private Wall wall;



    public TitleScreen(Activity activity) {;
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;


        System.out.println("HOYDE:" + screenHeight);
        System.out.println("BREDDE:" + screenWidth);
        backSprite1 = new Sprite(backgroundImage);
        backSprite2 = new Sprite(backgroundImage);
        backSprite3 = new Sprite(backgroundImage);
        backSprite1.setScale(200, 200);
        heliSprite = new Sprite(heliImage);
        aSprite = new Sprite(aImage);
        westWall = new Sprite(wallVerImage);
        westWall.setPosition(1, 380);
        eastWall = new Sprite(wallVerImage);
        eastWall.setPosition(screenWidth, 380f);
        topWall = new Sprite(wallVerImage);
        topWall.rotate(90);
        topWall.setPosition(screenWidth,375);
        bottomWall = new Sprite(wallVerImage);
        bottomWall.rotate(90);
        bottomWall.setPosition(screenWidth,screenHeight + 220);
        aSprite.setPosition(200, 120);
        aSprite.setSpeed(40, 0); // it should move right direction, but since collides bug, it will move (-40,0),  If we input (-40,0), it move (40,0), after collides, helicopter is disappeared. bug?
        heliSprite.setPosition(200, 220);
        heliSprite.setSpeed(-40, 0);


        Rect rectgle= new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int StatusBarHeight= rectgle.top;
        int contentViewTop=
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        TitleBarHeight= contentViewTop - StatusBarHeight;



    }

    public void draw(android.graphics.Canvas canvas){




        canvas.drawColor(Color.rgb(254, 0, 254));
        backSprite1.draw(canvas);
        Paint myPaint = new Paint();

        myPaint.setColor(Color.rgb(0, 0, 0));
        myPaint.setStyle(Paint.Style.STROKE);
        Sprite sprite = new Sprite();
//        rect = canvas.drawRect(3, 3, screenWidth - 5, canvas.getHeight() - 5, myPaint);
        Rect rectangle = new Rect();

//        topWall.draw(canvas);
//        bottomWall.draw(canvas);
//        westWall.draw(canvas);
//        eastWall.draw(canvas);
        aSprite.draw(canvas);
        heliSprite.draw(canvas);
    }

    public void update(float dt) {
//        System.out.println("aspirte: " + aSprite.getPosition().getX());
//        if (aSprite.getPosition().getX() >= (screenWidth - ((aImage.getWidth()/2) -5))){
//            System.out.println("crash east border!");
//            System.out.println("" + screenWidth);
//            System.out.println("LENGDE SPRITE" + aSprite.getBoundingBox().toString());
//            aSprite.setSpeed(-aSprite.getSpeed().getX(), aSprite.getSpeed().getY());
//        }

        if(aSprite.collides(eastWall))
        {
            System.out.println("crash east border!");
            System.out.println("" + screenWidth);
            System.out.println("LENGDE SPRITE" + aSprite.getBoundingBox().toString());
            aSprite.setSpeed(-aSprite.getSpeed().getX(), aSprite.getSpeed().getY());
        }

        else if(aSprite.collides(westWall)) // collides is true first time, and change the object direction.
        {
            System.out.println("crash west border!");
            System.out.println("LENGDE SPRITE" + aSprite.getBoundingBox());
            aSprite.setSpeed(-aSprite.getSpeed().getX(), aSprite.getSpeed().getY());
        }

//        else if(aSprite.getPosition().getX() <= ((aImage.getWidth()/2) - 5)) // collides is true first time, and change the object direction.
//        {
//            System.out.println("crash west border!");
//            System.out.println("LENGDE SPRITE" + aSprite.getBoundingBox());
//            aSprite.setSpeed(-aSprite.getSpeed().getX(), aSprite.getSpeed().getY());
//        }

        else if(aSprite.collides(heliSprite)) // This collides is judged since the above collides. First execution collides will be true at the first time without any judge.
        {
            System.out.println("crash each other!");
            aSprite.setSpeed(-aSprite.getSpeed().getX(), aSprite.getSpeed().getY());
            heliSprite.setScale(-1, 1);
            heliSprite.setPosition(heliSprite.getPosition().getX() + heliImage.getWidth(), heliSprite.getPosition().getY());
            heliSprite.setSpeed(-aSprite.getSpeed().getX(), aSprite.getSpeed().getY());
        }


        if(heliSprite.collides(eastWall))
        {
            System.out.println("crash east border!");
            System.out.println("Bredde bilde" + heliImage.getWidth());
            System.out.println("heli position X" + heliSprite.getPosition().getX());
            System.out.println();
//            heliSprite.setScale(1, -1);
//            heliSprite.setPosition(heliSprite.getPosition().getX() + heliImage.getWidth(), heliSprite.getPosition().getY());

            heliSprite.setSpeed(-heliSprite.getSpeed().getX(), heliSprite.getSpeed().getY());
        }
        else if(heliSprite.collides(westWall)) {
            System.out.println("crash west border!");
            System.out.println("" + screenWidth);
//            heliSprite.setScale(-1, 1);
//            heliSprite.setPosition(heliSprite.getPosition().getX() + heliImage.getWidth(), heliSprite.getPosition().getY());
            heliSprite.setSpeed(-heliSprite.getSpeed().getX(), heliSprite.getSpeed().getY());
        }

        aSprite.update(dt);
        heliSprite.update(dt);
        westWall.update(dt);
        eastWall.update(dt);
        topWall.update(dt);
        bottomWall.update(dt);
//        System.out.println("TOP POSITION: " + topWall.getPosition().getX());

    }
}
