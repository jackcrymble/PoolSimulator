package development.crymble.jack.poolsimulator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by jackc on 26/02/2017.
 */


public class Ball {

    public enum Colour {
        RED, YELLOW, BLACK, WHITE; //unsure if this is the best way to implement this.
    }

    private final float DECELERATION = 0.02f;
    private final float MINIMUMSPEED = 1.20f;

    private Vector2d position; //used to get balls position on the table
    private Vector2d velocity; //used to get velocity of the ball
    private Colour colour; //obviously different balls have different properties.
    private float radius; //Will be used for collision detection
    private boolean isMoving, isActive;
    private Bitmap bitmap;

    public Ball(float x, float y, Colour colour, Bitmap bitmap){
        this.position = new Vector2d(x, y);
        this.velocity = new Vector2d(0,0);
        this.colour = colour;
        this.isMoving = false;
        this.isActive = true;
        this.bitmap = bitmap;
        this.radius = bitmap.getHeight() / 2;
    }

    public Ball(float x, float y, Colour colour, Boolean toTheLeft, Bitmap bitmap){
        this.position = new Vector2d(x, y);
        this.velocity = new Vector2d(0,0);
        this.colour = colour;
        this.isMoving = false;
        this.isActive = true;
        this.bitmap = bitmap;
        this.radius = bitmap.getHeight() / 2;
        setTriangleCoords(x, y, toTheLeft);
    }

    public void draw(Canvas canvas){
            canvas.drawBitmap(bitmap, position.getX(), position.getY(), null);
    }

    public void update(){
        if(isMoving) {
           position = position.add(velocity);

            applyFriction();
            //ball in Q1
            if (velocity.getX() > 0 && velocity.getX() < MINIMUMSPEED && velocity.getY() > 0 && velocity.getY() < MINIMUMSPEED) {
                isMoving = false;
            }
            //ball in Q2
            else if (velocity.getX() > 0 && velocity.getX() < MINIMUMSPEED && velocity.getY() < 0 && velocity.getY() > -MINIMUMSPEED) {
                isMoving = false;
            }
            //ball in Q3
            else if (velocity.getX() < 0 && velocity.getX() > -MINIMUMSPEED && velocity.getY() < 0 && velocity.getY() > -MINIMUMSPEED) {
                isMoving = false;
            }
            //ball in Q4
            else if (velocity.getX() < 0 && velocity.getX() > -MINIMUMSPEED && velocity.getY() > 0 && velocity.getY() < MINIMUMSPEED) {
                isMoving = false;
            }
            if(velocity.getX() == 0 && velocity.getY() == 0){
                isMoving = false;
            }

            //print();
        }
    }

    public void hitSideCushion(){
        Vector2d temp = velocity;
        velocity = new Vector2d(temp.getX(), -temp.getY());
    }

    public void hitEndCushion(){
        Vector2d temp = velocity;
        velocity = new Vector2d(-temp.getX(), temp.getY());
    }

    public void hit(float angle, float force){
        isMoving = true;
        Movement m = new Movement(angle, force);
        this.velocity = m.getVelocity();
    }



    private void applyFriction(){
        Vector2d friction = new Vector2d(this.velocity.getX() * DECELERATION, this.velocity.getY() * DECELERATION);
        velocity = velocity.subtract(friction);
    }

    public void setTriangleCoords(float relativeX, float relativeY, boolean toTheLeft){

            //x coord will be the same no matter which side
            float tempX = relativeX + (bitmap.getWidth() * (float) Math.cos(Math.toRadians(30.0)));
            float tempY = 0.0f;

            //y coord will change.
            if(toTheLeft){
                //Ball is to be positioned behind this x, to the left
                tempY = relativeY + (bitmap.getHeight() * (float) Math.sin(Math.toRadians(30.0)));
            }
            else{
                //Ball is to be positioned behind, but to the right
                tempY = relativeY - (bitmap.getHeight() * (float) Math.sin(Math.toRadians(30.0)));
            }
        this.position = new Vector2d(tempX, tempY);
    }

    public void resolveCollision(Ball ball)
    {

        // get the mtd
        Vector2d delta = (position.subtract(ball.position));
        float r = getRadius() + ball.getRadius();
        float dist2 = delta.dot(delta);

        if (dist2 > r*r) return; // they aren't colliding


        float d = delta.getLength();

        Vector2d mtd;
        if (d != 0.0f)
        {
            mtd = delta.multiply(((getRadius() + ball.getRadius())-d)/d); // minimum translation distance to push balls apart after intersecting

        }
        else // Special case. Balls are exactly on top of eachother.  Don't want to divide by zero.
        {
            d = ball.getRadius() + getRadius() - 1.0f;
            delta = new Vector2d(ball.getRadius() + getRadius(), 0.0f);

            mtd = delta.multiply(((getRadius() + ball.getRadius())-d)/d);
        }

        // resolve intersection
        float m1 = 1; // inverse mass quantities
        float m2 = 1;

        // push-pull them apart
        position = position.add(mtd.multiply(m1 / (m1 + m2)));
        ball.position = ball.position.subtract(mtd.multiply(m2 / (m1 + m2)));

        // impact speed
        Vector2d v = (this.velocity.subtract(ball.velocity));
        float vn = v.dot(mtd.normalize());

        // sphere intersecting but moving away from each other already
        if (vn > 0.0f) return;

        // collision impulse
        float i = (-(1.0f + ScreenDimensions.restitution) * vn) / (m1 + m2);
        Vector2d impulse = mtd.multiply(i);

        // change in momentum
        this.isMoving = true;
        this.velocity = this.velocity.add(impulse);
        ball.isMoving = true;
        ball.velocity = ball.velocity.subtract(impulse);

        Log.d("Ball Collision:", "This: " + this.getColour() + ", Ball: " + ball.getColour());
    }

    //Getters

    public float getRadius() {
        return radius;
    }

    public Vector2d getPosition(){
        return position;
    }

    public Vector2d getVelocity(){
        return velocity;
    }

    public Colour getColour() {
        return colour;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public boolean isActive() { return isActive; }

    public boolean isMoving() { return isMoving;}

    //Setters

    public void setPosition(){

    }

    public void setMoving(){this.isMoving = true;}

    public void print(){
        System.out.printf("Colour: %s\nRadius: %f\nPosition: %s\nVelocity: %s\nIsMoving: %s\n", colour, radius, position.toString(), velocity.toString(), isMoving);
        System.out.println("--------------------------------------");
    }

}