package development.crymble.jack.poolsimulator;

/**
 * Created by jackc on 09/03/2017.
 */

public class ScreenDimensions {

    public static int screen_width;
    public static int screen_height;
    public static int playable_width;
    public static int playable_height;

    public static void setScreen_width(int screen_width) {
        ScreenDimensions.screen_width = screen_width;
    }

    public static void setScreen_height(int screen_height) {
        ScreenDimensions.screen_height = screen_height;
    }

    public static void setPlayable_width(int playable_width) {
        ScreenDimensions.playable_width = playable_width;
    }

    public static void setPlayable_height(int playable_height) {
        ScreenDimensions.playable_height = playable_height;
    }
}
