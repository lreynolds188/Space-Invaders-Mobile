package com.example.luke.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Background {

    private Bitmap image;
    private int x, y, yVelocity = -3;

    public Background(Bitmap resource)
    {
        image = resource;
    }

    public void update()
    {
        if (y <= -575)
        {
            y = 0;
        }
        y += yVelocity;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

}
