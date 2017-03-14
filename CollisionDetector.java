package development.crymble.jack.poolsimulator;

import java.util.ArrayList;
import java.util.Iterator;

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

        Iterator<Ball> iterator_i = allBalls.iterator();

        while(iterator_i.hasNext()) {

            Ball i = iterator_i.next();

            Iterator<Ball> iterator_j = allBalls.iterator();

            while(iterator_j.hasNext()) {

                Ball j = iterator_j.next();

                if(i.equals(j)) continue;

                if ((i.getPosition().getX() + i.getRadius()) < (j.getPosition().getX() - j.getRadius()))
                    break;

                if ((i.getPosition().getY() + i.getRadius()) < (j.getPosition().getY() - j.getRadius()) ||
                        (j.getPosition().getY() + j.getRadius()) < (i.getPosition().getY() - i.getRadius()))
                    continue;

                i.resolveCollision(j);

            }
        }
    }

    private void checkWallCollisions() {
        for (int i = 0; i < allBalls.size(); i++) {
            //Check collision on top and bottom cushions
            if (allBalls.get(i).getPosition().getX() - allBalls.get(i).getRadius() < ScreenDimensions.top_rail_x) { //Top Rail/Left of screen
                allBalls.get(i).getPosition().setX(ScreenDimensions.top_rail_x + allBalls.get(i).getRadius()); // Place ball against edge
                allBalls.get(i).hitEndCushion();
            }
            else if (allBalls.get(i).getPosition().getX() + allBalls.get(i).getRadius() > ScreenDimensions.bottom_rail_x){ // Bottom rail/Right of screen
                allBalls.get(i).getPosition().setX(ScreenDimensions.bottom_rail_x - allBalls.get(i).getRadius());// Place ball against edge
                allBalls.get(i).hitEndCushion();
            }
            
            //Check collision on side cushions
            if (allBalls.get(i).getPosition().getY() - allBalls.get(i).getRadius() < ScreenDimensions.right_rail_y){// Right rail/top of screen
                allBalls.get(i).getPosition().setY(ScreenDimensions.right_rail_y + allBalls.get(i).getRadius());// Place ball against edge
                allBalls.get(i).hitSideCushion();
            }
            else if (allBalls.get(i).getPosition().getY() + allBalls.get(i).getRadius() > ScreenDimensions.left_rail_y){ // Left Rail/bottom of screen
                allBalls.get(i).getPosition().setY(ScreenDimensions.left_rail_y - allBalls.get(i).getRadius());// Place ball against edge
                allBalls.get(i).hitSideCushion();
            }
        }
    }
}

