package com.example.luke.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Enemy {

    private Bitmap image;
    public float x,
                y,
                xVel = 0,
                yVel = 0,
                width = 0,
                height = 0,
                type,
                updateCount = 0,
                updateCheck = 0;

    public Enemy(Bitmap resource, int intX, int intY, int enemyType)
    {
        x = intX;
        y = intY;
        image = resource;
        width = resource.getWidth();
        height = resource.getHeight();
        type = enemyType;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update()
    {
        if(updateCheck % 20 == 0){
            x += xVel;
            y += yVel;
            updateCount++;
        }
        updateCheck++;
    }

    public void setVelocity(float newXvel, float newYvel){
        xVel = newXvel;
        yVel = newYvel;
    }

    public float[] hitbox()
    {
        float[] enemyHitbox = new float[4];
        enemyHitbox[0] = x;
        enemyHitbox[1] = y;
        enemyHitbox[2] = width;
        enemyHitbox[3] = height;
        return enemyHitbox;
    }

}
