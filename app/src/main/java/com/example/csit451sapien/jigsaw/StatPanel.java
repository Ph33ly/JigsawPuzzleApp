package com.example.csit451sapien.jigsaw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Brian on 4/18/2016.
 */
public class StatPanel extends View {

    private Paint paint = new Paint();

    public StatPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // background of canvas
        canvas.drawARGB(255, 0, 0, 500);

        // Labels
        paint.setARGB(255, 255, 255, 0);
        paint.setTextSize(50);

        String playedMsg = "Games Played:\t" + Integer.toString(SharedValues.timesPlayed);
        String wonMsg = "Games Won:\t" + Integer.toString(SharedValues.timesWon);

        canvas.drawText(playedMsg, canvas.getWidth()*(1/8f) - 50,canvas.getHeight()*(4/5f) + 50, paint);
        canvas.drawText(wonMsg, canvas.getWidth()*(3/5f) - 20f, canvas.getHeight()*(4/5f) + 50, paint);
        canvas.drawText(SharedValues.username, canvas.getWidth()*(1/3f) + 50, canvas.getHeight()*(4/5f) + 120, paint);

        // bar graph
        paint.setARGB(255, 500, 255, 500);
        canvas.drawRect(canvas.getWidth()*(1/5f), (canvas.getHeight()*( 4/5f)) - SharedValues.timesPlayed*10, canvas.getWidth()*(1/5f) + 100,
                canvas.getHeight()*(4/5f), paint);

        paint.setARGB(255, 255, 0, 500);
        canvas.drawRect(canvas.getWidth()*(3/5f) + 50, (canvas.getHeight()*(4/5f)) - SharedValues.timesWon*10, canvas.getWidth()*(3/5f) + 150,
                canvas.getHeight()*(4/5f), paint);

        invalidate();
    }
}
