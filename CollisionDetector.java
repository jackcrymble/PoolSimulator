package development.crymble.jack.poolsimulator;

import java.util.ArrayList;

/**
 * Created by jackc on 11/03/2017.
 */

public class CollisionDetector {

    private ArrayList<Ball> allBalls;

    public CollisionDetector(ArrayList<Ball> allBalls){
        this.allBalls = allBalls;
    }

    public void update(){
        checkBallCollisions();
        checkWallCollisions();
    }

    // Ball to Ball collision

    public void checkBallCollisions() {
        for (int i = 0; i < allBalls.size(); i++) {

            for (int j = i + 1; j < allBalls.size(); j++) {
                if ((allBalls.get(i).getPosition().getX() + allBalls.get(i).getRadius()) < (allBalls.get(j).getPosition().getX() - allBalls.get(j).getRadius()))
                    break;

                if ((allBalls.get(i).getPosition().getY() + allBalls.get(i).getRadius()) < (allBalls.get(j).getPosition().getY() - allBalls.get(j).getRadius()) ||
                        (allBalls.get(j).getPosition().getY() + allBalls.get(j).getRadius()) < (allBalls.get(i).getPosition().getY() - allBalls.get(i).getRadius()))
                    continue;

                allBalls.get(i).resolveCollision(allBalls.get(j));

            }
        }
    }

    private void checkWallCollisions() {
        for (int i = 0; i < allBalls.size(); i++) {
            if (allBalls.get(i).getPosition().getX() - allBalls.get(i).getRadius() < ScreenDimensions.top_rail_x) { //Left Wall
                allBalls.get(i).getPosition().setX(ScreenDimensions.top_rail_x + (allBalls.get(i).getRadius() * 2)); // Place ball against edge
                allBalls.get(i).hitEndCushion();
            }
            else if (allBalls.get(i).getPosition().getX() + allBalls.get(i).getRadius() > ScreenDimensions.bottom_rail_x){ // Right Wall
                allBalls.get(i).getPosition().setX(ScreenDimensions.bottom_rail_x - (allBalls.get(i).getRadius() * 2));// Place ball against edge
                allBalls.get(i).hitEndCushion();
            }

            if (allBalls.get(i).getPosition().getY() - allBalls.get(i).getRadius() < ScreenDimensions.right_rail_y){// Top Wall
                allBalls.get(i).getPosition().setY(ScreenDimensions.right_rail_y + (allBalls.get(i).getRadius() * 2));// Place ball against edge
                allBalls.get(i).hitSideCushion();
            }
            else if (allBalls.get(i).getPosition().getY() + allBalls.get(i).getRadius() > ScreenDimensions.left_rail_y){ // Bottom Wall
                allBalls.get(i).getPosition().setY(ScreenDimensions.left_rail_y - (allBalls.get(i).getRadius() * 2));// Place ball against edge
                allBalls.get(i).hitSideCushion();
            }
        }
    }
}

