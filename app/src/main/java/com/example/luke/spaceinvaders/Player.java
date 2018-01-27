package com.example.luke.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Player {

    private Bitmap image;
    public int x = 0,
               y = 0,
               xVel = 0,
               width = 0,
               height = 0,
               screenWidth = 0;

    public Player(Bitmap resource, int intX, int ScreenHeight, int ScreenWidth)
    {
        width = resource.getWidth();
        height = resource.getHeight();
        screenWidth = ScreenWidth;
        x = intX;
        y = (ScreenHeight / 20) * 19 - height;
        image = resource;
    }

    public void update()
    {
        if(xVel > 0 && x < screenWidth - width){
            x += xVel;
        } else if(xVel < 0 && x > 0){
            x += xVel;
        }
    }

    public int[] returnCoords()
    {
        int[] coords = new int[3];
        coords[0] = x;
        coords[1] = y;
        return coords;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

    public void setVelocity(int speed)
    {
        xVel = speed;
    }

    public float[] hitbox()
    {
        float[] playerHitbox = new float[4];
        playerHitbox[0] = x;
        playerHitbox[1] = y;
        playerHitbox[2] = width;
        playerHitbox[3] = height;
        return playerHitbox;
    }
}
