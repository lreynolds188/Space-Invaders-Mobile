package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Gameover {

    private Paint paint;
    private int screenWidth, screenHeight, currentScore;
    private Rect rectNewGame, rectMenu;
    public boolean newGameDown, menuDown;

    public Gameover(int w, int h, int score, Typeface cosmicAlien)
    {
        screenHeight = h;
        screenWidth = w;
        currentScore = score;
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(cosmicAlien);

        rectNewGame = new Rect(w/5, h/6*4, w/5*4, h/6*4 + h/24*3);
        rectMenu = new Rect(w/5, h/6*5, w/5*4, h/6*5 + h/24*3);
    }

    public void draw(Canvas canvas) {

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        paint.setStrokeWidth(1);
        canvas.drawText("Game Over!", screenWidth / 2, screenHeight / 6, paint);
        canvas.drawText("You scored: " + currentScore, screenWidth / 2, screenHeight / 6 + 125, paint);

        paint.setTextSize(70);
        changeColor(newGameDown);
        canvas.drawText("New Game", screenWidth / 2, screenHeight / 48 * 36, paint);
        changeColor(menuDown);
        canvas.drawText("Menu", screenWidth / 2, screenHeight / 48 * 44, paint);

        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        changeColor(newGameDown);
        canvas.drawRect(rectNewGame, paint);
        changeColor(menuDown);
        canvas.drawRect(rectMenu, paint);
    }

    public void changeColor(boolean btnDown){
        if(btnDown){
            paint.setColor(Color.WHITE);
        } else {
            paint.setColor(Color.GREEN);
        }
    }

}
