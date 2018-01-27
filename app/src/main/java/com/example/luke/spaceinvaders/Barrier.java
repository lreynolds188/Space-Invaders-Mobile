package com.example.luke.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Barrier {

    private ArrayList<Bitmap> image;
    public int lives = 0;
    public float x = 0,
            y = 0,
            width = 0,
            height = 0;

    public Barrier(float fX, float fY, ArrayList<Bitmap> resource){
        x = fX;
        y = fY;
        image = resource;
        width = image.get(2).getWidth();
        height = image.get(2).getHeight();
        lives = 3;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image.get(lives - 1), x, y, null);
    }

    public float[] hitbox()
    {
        float[] barrierHitbox = new float[4];
        barrierHitbox[0] = x;
        barrierHitbox[1] = y;
        barrierHitbox[2] = width;
        barrierHitbox[3] = height;
        return barrierHitbox;
    }


}
