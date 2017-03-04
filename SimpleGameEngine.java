package development.crymble.jack.poolsimulator;

/**
 * Created by jackc on 26/02/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class SimpleGameEngine extends Activity {

    // gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view
        gameView = new GameView(this);
        setContentView(gameView);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    // GameView class will go here

    // Here is our implementation of GameView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside SimpleGameEngine

    // Notice we implement runnable so we have
    // A thread and can override the run method.
    class GameView extends SurfaceView implements Runnable {

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // Declare an object of type Bitmap
        Bitmap bitmapCueBall;
        Bitmap bitmapRedBall;
        Bitmap bitmapYellowBall;
        Bitmap bitmapBlackBall;

        //Declare a Ball object
        Ball cueBall;
        Ball redBall1, redBall2, redBall3, redBall4, redBall5, redBall6, redBall7;
        Ball yellowBall1, yellowBall2, yellowBall3, yellowBall4, yellowBall5, yellowBall6, yellowBall7;
        Ball blackBall;

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public GameView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            //Get height and width of game screen to position objects accordingly
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            // Load balls from .png files
            bitmapCueBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.cueball);
            bitmapRedBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.redball);
            bitmapYellowBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.yellowball);
            bitmapBlackBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.blackball);

            //Initialise cueBall object
            cueBall = new Ball((width / 4.0f) - bitmapCueBall.getWidth() / 2, (height / 2.0f) - bitmapCueBall.getHeight() / 2, Ball.Colour.WHITE, bitmapCueBall);

            float initialBallX = (width / 4.0f) * 3 - bitmapRedBall.getWidth() / 2;
            float initialBallY = (height / 2.0f) - bitmapRedBall.getHeight() / 2;

            //Initialise left most column of triangle
            redBall1 = new Ball(initialBallX, initialBallY, Ball.Colour.RED, bitmapRedBall);
            yellowBall1 = new Ball(redBall1.getX(), redBall1.getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);
            redBall2 = new Ball(yellowBall1.getX(), yellowBall1.getY(), Ball.Colour.RED, true, bitmapRedBall);
            yellowBall2 = new Ball(redBall2.getX(), redBall2.getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);
            redBall3 = new Ball(yellowBall2.getX(), yellowBall2.getY(), Ball.Colour.RED, true, bitmapRedBall);

            //Initialise second column of triangle
            redBall4 = new Ball(redBall1.getX(), redBall1.getY(), Ball.Colour.RED, false, bitmapRedBall);
            blackBall = new Ball(redBall4.getX(), redBall4.getY(), Ball.Colour.BLACK, true, bitmapBlackBall);
            redBall5 = new Ball(blackBall.getX(), blackBall.getY(), Ball.Colour.RED, true, bitmapRedBall);
            yellowBall3 = new Ball(redBall5.getX(), redBall5.getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);

            //Initialise third column of triangle
            yellowBall4 = new Ball(redBall4.getX(), redBall4.getY(), Ball.Colour.YELLOW, false, bitmapYellowBall);
            yellowBall5 = new Ball(yellowBall4.getX(), yellowBall4.getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);
            redBall6 = new Ball(yellowBall5.getX(), yellowBall5.getY(), Ball.Colour.RED, true, bitmapRedBall);

            //Initialise forth column of triangle
            redBall7 = new Ball(yellowBall4.getX(), yellowBall4.getY(), Ball.Colour.RED, false, bitmapRedBall);
            yellowBall6 = new Ball(redBall7.getX(), redBall7.getY(), Ball.Colour.YELLOW, true, bitmapYellowBall);

            //Initialise fifth column of triangle
            yellowBall7 = new Ball(redBall7.getX(), redBall7.getY(), Ball.Colour.YELLOW, false, bitmapYellowBall);

            // Set our boolean to true - game on!
            playing = true;

        }

        @Override
        public void run() {
            while (playing) {

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                update();

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }

            }

        }

        // Everything that needs to be updated goes in here
        // In later projects we will have dozens (arrays) of objects.
        // We will also do other things like collision detection.
        public void update() {
            cueBall.update();
        }

        // Draw the newly updated scene
        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawColor(Color.argb(255, 26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 249, 129, 0));

                // Make the text a bit bigger
                paint.setTextSize(45);

                //Display the FPS
                canvas.drawText("FPS: " + fps, 20, 40, paint);

                //Draw cueball at its current position
                cueBall.draw(canvas);

                redBall1.draw(canvas);
                redBall2.draw(canvas);
                redBall3.draw(canvas);
                redBall4.draw(canvas);
                redBall5.draw(canvas);
                redBall6.draw(canvas);
                redBall7.draw(canvas);

                yellowBall1.draw(canvas);
                yellowBall2.draw(canvas);
                yellowBall3.draw(canvas);
                yellowBall4.draw(canvas);
                yellowBall5.draw(canvas);
                yellowBall6.draw(canvas);
                yellowBall7.draw(canvas);

                blackBall.draw(canvas);

                // Draw everything to the screen
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started then
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
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

    // More SimpleGameEngine methods will go here

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }

}
