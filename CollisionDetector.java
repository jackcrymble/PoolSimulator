package development.crymble.jack.poolsimulator;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jackc on 11/03/2017.
 */

public class CollisionDetector {

    private ArrayList<Ball> allBalls;
    private ArrayList<Ball> movingBalls;

    public CollisionDetector(ArrayList<Ball> allBalls){

        this.allBalls = allBalls;

    }

    public ArrayList<Ball> update(){
        movingBalls = new ArrayList<>();

        for(Ball ball : allBalls) {
            if (ball.isMoving()) {
                movingBalls.add(ball);
            }
        }

        for(int i = 0; i < movingBalls.size(); i++){
            for(int j = 1; j < allBalls.size(); j++){
                if(checkCollision(allBalls.get(i), allBalls.get(j))){
                    ballCollision(allBalls.get(i), allBalls.get(j));
                }
            }
        }
        return allBalls;
    }

    public void ballCollision(Ball one, Ball two){
        two.setMoving();
        two.setVx(one.getVx());
        two.setVy(one.getVy());
        one.setVx(one.getVx() / 2);
        one.setVy(one.getVy() / 2);
    }

    public boolean checkCollision(Ball one, Ball two){

        //Most likely the same ball
        if(one.getX() == two.getX() && one.getY() == two.getY()){
            return false;
        }

        if(one.getVx() == 0 || one.getVy() == 0){
            return false;
        }

        double dx = one.getX() - two.getX();
        double dy = one.getY() - two.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance < one.getRadius() + two.getRadius()){
            Log.d(("Collision detection"), "Collision between two allBalls.");
            return true;
        }
        return false;
    }

}
