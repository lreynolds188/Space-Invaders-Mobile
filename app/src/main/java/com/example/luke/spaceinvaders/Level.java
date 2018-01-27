package com.example.luke.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Level {

    int level = 1;
    Paint paint;
    int screenWidth;

    public Level(int width, Typeface cosmicAlien)
    {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTypeface(cosmicAlien);
        paint.setTextSize(45);
        screenWidth = width;
    }

    public void draw(Canvas canvas)
    {
        paint.setColor(Color.WHITE);
        canvas.drawText("Level: ", 10, 120, paint);
        paint.setColor(Color.GREEN);
        canvas.drawText("" + level, 240, 120, paint);
    }

    public void update(int x)
    {
        level = x;
    }

}
