package com.example.luke.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Bullet {

    private Bitmap image;
    public int x = 0,
                y = 0,
                yVel = -2,
                updateCount = 0,
                width = 0,
                height = 0;

    public Bullet(Bitmap resource, int intX, int intY)
    {
        image = resource;
        x = intX;
        y = intY;
        width = resource.getWidth();
        height = resource.getHeight();
    }

    public void update()
    {
        updateCount++;
        y += yVel * updateCount;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

    public float[] hitbox()
    {
        float[] bulletHitbox = new float[4];
        bulletHitbox[0] = x;
        bulletHitbox[1] = y;
        bulletHitbox[2] = width;
        bulletHitbox[3] = height;
        return bulletHitbox;
    }

}
