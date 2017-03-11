package development.crymble.jack.poolsimulator;

/**
 * Created by jackc on 26/02/2017.
 */
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;

        ScreenDimensions.setScreen_height(height);
        ScreenDimensions.setScreen_width(width);

        //TODO: Fix multipliers. These will do for now.
        //Note: 0,0 coords are in top left corner of screen
        ScreenDimensions.setTop_rail_x(width * 0.041f);
        ScreenDimensions.setBottom_rail_x(width * (1 - 0.041f));
        ScreenDimensions.setRight_rail_y(height * 0.114f);
        ScreenDimensions.setLeft_rail_y(height * (1 - 0.114f));


        setContentView(new GameView(this));
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();
    }

}
