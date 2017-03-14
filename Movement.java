package development.crymble.jack.poolsimulator;

/**
 * Created by jackc on 26/02/2017.
 */

public class Movement {

    private enum Quadrant {
        Q1, Q2, Q3, Q4;
    }

    private final float MINFORCE = 0.0f;
    private final float MAXFORCE = 50.0f;

    private Quadrant quadrant;
    private float angle;
    private float qAngle;
    private float force;
    private float temp1, temp2;
    private float vx, vy;

    /**
     * Constructor which will associate all variables with their values
     *
     * Will print out variables.
     *
     * @param angle at which the ball is hit
     *
     * @param force with which the ball is hit. Acts as a multiplier to the initial vx and vy values.
     */
    public Movement(float angle, float force){
        this.angle = angle % 360;
        this.force = force;
        calcQAngle(); // get the angle for that quadrant.
        calcQuadrant();
        calcTemps();
        assignTemps();
        applyForce();
        print();
    }

    /**
     * Calculate the quadrant based on the angle given
     */
    private void calcQAngle(){
        if(angle == 0){
            qAngle = 0.0f;
        }
        else if(angle % 90.0f == 0){
            qAngle = 90.0f;
        }
        else{
            qAngle = angle % 90.0f;
        }
    }

    /**
     * Set the quadrant based on the given angle.
     */
    private void calcQuadrant(){

        if(angle >= 0 && angle <= 90){
            quadrant = Quadrant.Q1;
        }
        else if(angle > 90 && angle <= 180){
            quadrant = Quadrant.Q2;
        }
        else if(angle > 180 && angle <= 270){
            quadrant = Quadrant.Q3;
        }
        else if(angle > 270 && angle <= 360){
            quadrant = Quadrant.Q4;
        }
        else{
            System.out.println("Not valid angle");
        }
    }

    /**
     * Calculate the temp1 and temp2 variables based on the qAngle
     */
    public void calcTemps(){
        temp1 = (qAngle * 10)/900.0f;
        temp2 = 1.0f - temp1;
    }

    /**
     * Assign the temp1 and temp2 values to either vx or vy based on the quadrant.
     */
    public void assignTemps(){
        switch(quadrant){

            case Q1:
                vx = temp1;
                vy = temp2;
                break;
            case Q2:
                vy = temp1;
                vx = temp2;
                break;
            case Q3:
                vx = -temp1;
                vy = -temp2;
                break;
            case Q4:
                vy = temp1;
                vx = -temp2;
                break;
            default:
                System.err.println("Quadrant not recognised. Unknown error");
                break;
        }
    }

    /**
     * Apply the multiplier "force" to the vx and vy values
     */
    private void applyForce(){
        if(force < MINFORCE || force > MAXFORCE){
            System.err.println(force + " is not a valid force.");
        }
        vx *= force;
        vy *= force;
    }

    public Vector2d getVelocity(){
        return new Vector2d(vx, vy);
    }

    public void print(){
        System.out.printf("Angle: %f\nqAngle: %f\nForce: %f\nVx: %f, Vy: %f\n", angle, qAngle, force, vx, vy);
        System.out.println("--------------------------------------");
    }
}
