package com.example.luke.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Score {

    int score = 0;
    Paint paint;
    int screenWidth;

    public Score(int width, Typeface cosmicAlien)
    {
        paint = new Paint();
        paint.setTypeface(cosmicAlien);
        paint.setTextSize(45);
        screenWidth = width;
    }

    public void draw(Canvas canvas)
    {
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: ", screenWidth - 500, 60, paint);
        paint.setColor(Color.GREEN);
        canvas.drawText("" + score, screenWidth - 275, 60, paint);
    }

    public void update(int x)
    {
        score += x;
    }

}
