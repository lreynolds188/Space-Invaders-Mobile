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

public class Lives {

    Bitmap image;
    int lives = 3;
    Paint paint;
    int screenWidth;

    public Lives(int width, Typeface cosmicAlien, Bitmap resource)
    {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTypeface(cosmicAlien);
        paint.setTextSize(45);
        screenWidth = width;
        image = resource;
    }

    public void draw(Canvas canvas)
    {
        paint.setColor(Color.WHITE);
        canvas.drawText("Lives: ", 10, 60, paint);
        paint.setColor(Color.GREEN);
        for(int i = 0; i < lives; i++){
            canvas.drawBitmap(image, 240 + (i * 100), 20, null);
        }

    }

    public void update(int x)
    {
        lives += x;
    }

}
