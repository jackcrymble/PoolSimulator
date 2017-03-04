package development.crymble.jack.poolsimulator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jackc on 26/02/2017.
 */


public class Ball {

    public enum Colour {
        RED, YELLOW, BLACK, WHITE; //unsure if this is the best way to implement this.
    }

    private final float DECELERATION = 0.02f;
    private final float MINIMUMSPEED = 1.25f;
    private final float MAXIMUMSPEED = 50.0f;

    private float x, y; //used to get balls position on the table
    private float vx, vy; //used to get velocity of the ball
    private Colour colour; //obviously different balls have different properties.
    private float radius; //Will be used for collision detection
    private boolean isMoving;
    private Bitmap bitmap;

    public Ball(float x, float y, Colour colour, Bitmap bitmap){
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.isMoving = false;
        this.bitmap = bitmap;
        this.radius = bitmap.getHeight() / 2;
    }

    public Ball(float x, float y, Colour colour, Boolean toTheLeft, Bitmap bitmap){
        this.colour = colour;
        this.isMoving = false;
        this.bitmap = bitmap;
        this.radius = bitmap.getHeight() / 2;
        setTriangleCoords(x, y, toTheLeft);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, null);
        if((x + vx)< 0 || (x + bitmap.getWidth() + vx) > canvas.getWidth()){
            hitEndCushion();
        }
        if((y + vy) < 0 || (y  + bitmap.getHeight() + vy) > canvas.getHeight()){
            hitSideCushion();
        }
    }

    public void update(){
        if(isMoving) {
            x += vx;
            y += vy;
            applyFriction();
            //ball in Q1
            if (vx > 0 && vx < MINIMUMSPEED && vy > 0 && vy < MINIMUMSPEED) {
                isMoving = false;
            }
            //ball in Q2
            else if (vx > 0 && vx < MINIMUMSPEED && vy < 0 && vy > -MINIMUMSPEED) {
                isMoving = false;
            }
            //ball in Q3
            else if (vx < 0 && vx > -MINIMUMSPEED && vy < 0 && vy > -MINIMUMSPEED) {
                isMoving = false;
            }
            //ball in Q4
            else if (vx < 0 && vx > -MINIMUMSPEED && vy > 0 && vy < MINIMUMSPEED) {
                isMoving = false;
            }

            print();
        }
    }

    public void hitSideCushion(){
        vy = -vy;
    }

    public void hitEndCushion(){
        vx = -vx;
    }

    public void hit(float angle, float force){
        isMoving = true;
        Movement m = new Movement(angle, force);
        this.vx = m.getVx();
        this.vy = m.getVy();
    }

    private void applyFriction(){
        vx -= (vx * DECELERATION);
        vy -= (vy * DECELERATION);
    }

    public void setTriangleCoords(float relativeX, float relativeY, boolean toTheLeft){

            //x coord will be the same no matter which side
            this.x = relativeX + (bitmap.getWidth() * (float) Math.cos(Math.toRadians(30.0)));

            //y coord will change.
            if(toTheLeft){
                //Ball is to be positioned behind this x, to the left
                this.y = relativeY + (bitmap.getHeight() * (float) Math.sin(Math.toRadians(30.0)));
            }
            else{
                //Ball is to be positioned behind, but to the right
                this.y = relativeY - (bitmap.getHeight() * (float) Math.sin(Math.toRadians(30.0)));
            }
    }

    //Getters

    public float getRadius() {
        return radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public Colour getColour() {
        return colour;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public boolean isMoving(){
        return isMoving;
    }

    //Setters

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public void print(){
        System.out.printf("Colour: %s\nRadius: %f\nX: %f\tY: %f\nVX: %f\tVY: %f\n", colour, radius, x, y, vx, vy);
        System.out.println("--------------------------------------");
    }

}