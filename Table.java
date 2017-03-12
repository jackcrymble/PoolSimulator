package development.crymble.jack.poolsimulator;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jackc on 26/02/2017.
 */

public class Table {

    private Bitmap bitmap;
    private final float width = ScreenDimensions.screen_width;
    private final float height = ScreenDimensions.screen_height;

    public Table(Bitmap bitmap){
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

}
