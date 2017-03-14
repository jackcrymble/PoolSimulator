package development.crymble.jack.poolsimulator;

/**
 * Created by jackc on 09/03/2017.
 *
 *          P1----C2------P2------C3-------P3
 *          |     |                        |
 *          |     |                        |
 *          C1    |                *       C4
 *          |     |                        |
 *          |     |                        |
 *          P6----C6------P5------C5-------P4
 *
 *          Where C# is a cushion with its own values and P# is a pocket with its own values.
 *          This should allow for a ball to miss the cushion and go into the pocket.
 *
 */

public class ScreenDimensions {

    public static final float restitution = 0.85f;

    public static float screen_width;
    public static float screen_height;

    //C1
    public static float top_rail_x;
    public static float top_rail_y;
    public static float top_rail_length;

    //C4
    public static float bottom_rail_x;
    public static float bottom_rail_y;
    public static float bottom_rail_length;

    //C5 && C6
    public static float left_rail_x;
    public static float left_rail_y;
    public static float left_rail_length;

    //C2 && C3
    public static float right_rail_x;
    public static float right_rail_y;
    public static float right_rail_length;

    //All pockets have the same radius
    public static float P_radius;
    
    //P1
    public static float P1_x;
    public static float P1_y;

    //P2
    public static float P2_x;
    public static float P2_y;

    //P3
    public static float P3_x;
    public static float P3_y;

    //P4
    public static float P4_x;
    public static float P4_y;

    //P5
    public static float P5_x;
    public static float P5_y;

    //P6
    public static float P6_x;
    public static float P6_y;

    public static void setScreen_width(float screen_width) {
        ScreenDimensions.screen_width = screen_width;
    }

    public static void setScreen_height(float screen_height) {
        ScreenDimensions.screen_height = screen_height;
    }

    public static void setTop_rail_x(float top_rail_x) {
        ScreenDimensions.top_rail_x = top_rail_x;
    }

    public static void setTop_rail_y(float top_rail_y) {
        ScreenDimensions.top_rail_y = top_rail_y;
    }

    public static void setTop_rail_length(float top_rail_length) {
        ScreenDimensions.top_rail_length = top_rail_length;
    }

    public static void setBottom_rail_x(float bottom_rail_x) {
        ScreenDimensions.bottom_rail_x = bottom_rail_x;
    }

    public static void setBottom_rail_y(float bottom_rail_y) {
        ScreenDimensions.bottom_rail_y = bottom_rail_y;
    }

    public static void setBottom_rail_length(float bottom_rail_length) {
        ScreenDimensions.bottom_rail_length = bottom_rail_length;
    }

    public static void setLeft_rail_x(float left_rail_x) {
        ScreenDimensions.left_rail_x = left_rail_x;
    }

    public static void setLeft_rail_y(float left_rail_y) {
        ScreenDimensions.left_rail_y = left_rail_y;
    }

    public static void setLeft_rail_length(float left_rail_length) {
        ScreenDimensions.left_rail_length = left_rail_length;
    }

    public static void setRight_rail_x(float right_rail_x) {
        ScreenDimensions.right_rail_x = right_rail_x;
    }

    public static void setRight_rail_y(float right_rail_y) {
        ScreenDimensions.right_rail_y = right_rail_y;
    }

    public static void setRight_rail_length(float right_rail_length) {
        ScreenDimensions.right_rail_length = right_rail_length;
    }
}
