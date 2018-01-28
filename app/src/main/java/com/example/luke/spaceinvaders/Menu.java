package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class Menu extends SurfaceView implements SurfaceHolder.Callback{

    private Bitmap bmpHeading;
    private int screenWidth, screenHeight;
    private Rect rectNewGame, rectHighScores, rectHelp, rectSettings;
    private MenuThread mThread;
    private Context context;
    private AssetManager am;
    private Paint paint;
    private boolean newGameDown, highScoresDown, helpDown, settingsDown;

    public Menu(Context context, int width, int height){
        super(context);

        this.context = context;

        paint = new Paint();

        bmpHeading = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header), width, (height / 6)*2, true);
        am = context.getAssets();
        paint.setTypeface(Typeface.createFromAsset(am, "fonts/cosmicAlien.ttf"));

        mThread = new MenuThread(getHolder(), this);
        StartThreads();

        screenWidth =  width;
        screenHeight = height;
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(70);
        rectNewGame = new Rect(5, (height / 6)*2, width - 5, (height / 6) *3 - 10);
        rectHighScores = new Rect(5, (height / 6)*3, width - 5, (height / 6) *4 - 10);
        rectHelp = new Rect(5, (height / 6)*4, width - 5, (height / 6) *5 - 10);
        rectSettings = new Rect(5, (height / 6)*5, width - 5, height - 10);
    }

    public void StartThreads(){
        mThread.setRunning(true);
        mThread.start();
        System.out.println("Menu thread started.");
    }

    public void StopThreads(){
        while(true){
            try{
                mThread.setRunning(false);
                System.out.println("Menu thread stopped.");
                break;
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(bmpHeading, 0, 0, null);

        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        changeColor(newGameDown);
        canvas.drawRect(rectNewGame, paint);

        changeColor(highScoresDown);
        canvas.drawRect(rectHighScores, paint);

        changeColor(helpDown);
        canvas.drawRect(rectHelp, paint);

        changeColor(settingsDown);
        canvas.drawRect(rectSettings, paint);

        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);

        changeColor(newGameDown);
        canvas.drawText("New Game", screenWidth / 2, screenHeight / 12 * 5 + 10, paint);

        changeColor(highScoresDown);
        canvas.drawText("High Scores", screenWidth / 2, screenHeight / 12 * 7 + 10, paint);

        changeColor(helpDown);
        canvas.drawText("Help", screenWidth / 2, screenHeight / 12 * 9 + 10, paint);

        changeColor(settingsDown);
        canvas.drawText("Settings", screenWidth / 2, screenHeight / 12 * 11 + 10, paint);

        changeColor(false);
    }

    public void changeColor(boolean bln){
        if(bln){
            paint.setColor(Color.GREEN);
        } else {
            paint.setColor(Color.WHITE);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        StartThreads();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        StopThreads();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP){
            newGameDown = false;
            highScoresDown = false;
            helpDown = false;
            settingsDown = false;

            if(event.getY() > screenHeight / 6 * 2 && event.getY() < screenHeight / 6 * 3){
                Intent i = new Intent(context, GameInit.class);
                context.startActivity(i);
            } else if (event.getY() > screenHeight / 6 * 3 && event.getY() < screenHeight / 6 * 4){
                Intent i = new Intent(context, HighScoresInit.class);
                context.startActivity(i);
            } else if (event.getY() > screenHeight / 6 * 4 && event.getY() < screenHeight / 6 * 5){
                Intent i = new Intent(context, HelpInit.class);
                context.startActivity(i);
            } else if (event.getY() > screenHeight / 6 * 5 && event.getY() < screenHeight){
                Intent i = new Intent(context, SettingsInit.class);
                context.startActivity(i);
            }
        } else if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
            if(event.getY() > screenHeight / 6 * 2 && event.getY() < screenHeight / 6 * 3){
                newGameDown = true;
            } else if (event.getY() > screenHeight / 6 * 3 && event.getY() < screenHeight / 6 * 4){
                highScoresDown = true;
            } else if (event.getY() > screenHeight / 6 * 4 && event.getY() < screenHeight / 6 * 5){
                helpDown = true;
            } else if (event.getY() > screenHeight / 6 * 5 && event.getY() < screenHeight){
                settingsDown = true;
            }
        }

        return true;
    }
}
