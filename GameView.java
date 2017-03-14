package development.crymble.jack.poolsimulator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jackc on 09/03/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    Table table;

    // Declare an object of type Bitmap
    private Bitmap bitmapTable, bitmapCueBall, bitmapRedBall, bitmapYellowBall, bitmapBlackBall;

    //Declare a Ball object
    Ball cueBall;
    Ball redBall1, redBall2, redBall3, redBall4, redBall5, redBall6, redBall7;
    Ball yellowBall1, yellowBall2, yellowBall3, yellowBall4, yellowBall5, yellowBall6, yellowBall7;
    Ball blackBall;

    //Declare ArrayList for activeBalls
    private ArrayList<Ball> activeBalls;

    private CollisionDetector collisionDetector;

    public GameView(Context context) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        // Load bitmaps from .png files
        bitmapTable = BitmapFactory.decodeResource(this.getResources(), R.drawable.table);
        bitmapCueBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.cueball);
        bitmapRedBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.redball);
        bitmapYellowBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.yellowball);
        bitmapBlackBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.blackball);

        //Initialise Table object
        table = new Table(bitmapTable);

        //Initialise cueBall object
        cueBall = new Ball((ScreenDimensions.screen_width / 4.0f) - bitmapCueBall.getWidth() / 2, (ScreenDimensions.screen_height / 2.0f) - bitmapCueBall.getHeight() / 2, Ball.Colour.WHITE, bitmapCueBall);

        float initialBallX = ((ScreenDimensions.screen_width / 4.0f) * 3 - bitmapRedBall.getWidth() / 2) - 86.0f;
        float initialBallY = (ScreenDimensions.screen_height / 2.0f) - bitmapRedBall.getHeight() / 2;

        //Initialise left most column of triangle
        redBall1 = new Ball(initialBallX, initialBallY, Ball.Colour.RED, bitmapRedBall);
        yellowBall1 = new Ball(redBall1.getPosition().getX(), redBall1.getPosition().getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);
        redBall2 = new Ball(yellowBall1.getPosition().getX(), yellowBall1.getPosition().getY(), Ball.Colour.RED, true, bitmapRedBall);
        yellowBall2 = new Ball(redBall2.getPosition().getX(), redBall2.getPosition().getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);
        redBall3 = new Ball(yellowBall2.getPosition().getX(), yellowBall2.getPosition().getY(), Ball.Colour.RED, true, bitmapRedBall);

        //Initialise second column of triangle
        redBall4 = new Ball(redBall1.getPosition().getX(), redBall1.getPosition().getY(), Ball.Colour.RED, false, bitmapRedBall);
        blackBall = new Ball(redBall4.getPosition().getX(), redBall4.getPosition().getY(), Ball.Colour.BLACK, true, bitmapBlackBall);
        redBall5 = new Ball(blackBall.getPosition().getX(), blackBall.getPosition().getY(), Ball.Colour.RED, true, bitmapRedBall);
        yellowBall3 = new Ball(redBall5.getPosition().getX(), redBall5.getPosition().getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);

        //Initialise third column of triangle
        yellowBall4 = new Ball(redBall4.getPosition().getX(), redBall4.getPosition().getY(), Ball.Colour.YELLOW, false, bitmapYellowBall);
        yellowBall5 = new Ball(yellowBall4.getPosition().getX(), yellowBall4.getPosition().getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);
        redBall6 = new Ball(yellowBall5.getPosition().getX(), yellowBall5.getPosition().getY(), Ball.Colour.RED, true, bitmapRedBall);

        //Initialise forth column of triangle
        redBall7 = new Ball(yellowBall4.getPosition().getX(), yellowBall4.getPosition().getY(), Ball.Colour.RED, false, bitmapRedBall);
        yellowBall6 = new Ball(redBall7.getPosition().getX(), redBall7.getPosition().getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);

        //Initialise fifth column of triangle
        yellowBall7 = new Ball(redBall7.getPosition().getX(), redBall7.getPosition().getY(), Ball.Colour.YELLOW, false, bitmapYellowBall);

        activeBalls = new ArrayList<>();

        activeBalls.add(cueBall);
        activeBalls.add(blackBall);
        activeBalls.add(redBall1);
        activeBalls.add(redBall2);
        activeBalls.add(redBall3);
        activeBalls.add(redBall4);
        activeBalls.add(redBall5);
        activeBalls.add(redBall6);
        activeBalls.add(redBall7);
        activeBalls.add(yellowBall1);
        activeBalls.add(yellowBall2);
        activeBalls.add(yellowBall3);
        activeBalls.add(yellowBall4);
        activeBalls.add(yellowBall5);
        activeBalls.add(yellowBall6);
        activeBalls.add(yellowBall7);

        collisionDetector = new CollisionDetector(activeBalls);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;

        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            } catch(Exception e){
                e.printStackTrace();
            }
            retry = false;

        }
    }

    // Everything that needs to be updated goes in here
    // In later projects we will have dozens (arrays) of objects.
    // We will also do other things like collision detection.
    public void update() {

        collisionDetector.update();

        for(Ball ball : activeBalls){
            ball.update();
        }
    }

    // Draw the newly updated scene
    public void draw(Canvas canvas) {

        table.draw(canvas);

        for(Ball ball : activeBalls){
            ball.draw(canvas);
        }

    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            //Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                Random rand = new Random();
                float angle = (float) rand.nextInt(360);

                //hit ball at random angle at max power
                cueBall.hit(angle, 50.0f);

                break;
        }
        return true;
    }

}
// This is the end of our GameView inner class
