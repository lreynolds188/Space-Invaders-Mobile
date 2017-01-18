package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 9/07/2016.
 */
public class Settings extends SurfaceView implements SurfaceHolder.Callback{

    int currentWidth, currentHeight;
    Rect rectShowButtonBorders, rectEnableSound, rectEnableBackground, rectEnableMusic;
    Background bg;
    Paint paint = new Paint();
    private SettingsThread sThread;
    private AssetManager am;
    private List<String[]> config;
    public boolean blnShowBorders, blnEnableSound, blnEnableBackground, blnEnableMusic;
    Context context;

    public Settings(Context context, int screenWidth, int screenHeight){

        super(context);

        config = Utils.LoadConfig(context);
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        blnShowBorders = config.get(0)[1].contains("true");
        blnEnableSound = config.get(1)[1].contains("true");
        blnEnableMusic = config.get(2)[1].contains("true");
        blnEnableBackground = config.get(3)[1].contains("true");

        this.context = context;
        am = context.getAssets();
        currentHeight = screenHeight;
        currentWidth = screenWidth;

        sThread = new SettingsThread(getHolder(), this);
        StartThreads();

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setTypeface(Typeface.createFromAsset(am, "fonts/cosmicAlien.ttf"));
        paint.setTextSize(70);
        paint.setTextAlign(Paint.Align.LEFT);
        // LEFT TOP RIGHT BOTTOM
        rectShowButtonBorders = new Rect(currentWidth / 16 , currentHeight / 16 * 3, currentWidth / 16 + 125, currentHeight / 16 * 3 + 125);
        rectEnableSound = new Rect(currentWidth / 16 , currentHeight / 16 * 5, currentWidth / 16 + 125, currentHeight / 16 * 5 + 125);
        rectEnableMusic = new Rect(currentWidth / 16 , currentHeight / 16 * 7, currentWidth / 16 + 125, currentHeight / 16 * 7 + 125);
        rectEnableBackground = new Rect(currentWidth / 16 , currentHeight / 16 * 9, currentWidth / 16 + 125, currentHeight / 16 * 9 + 125);

        // enable access to the form
        setFocusable(true);

        // listen for user input events
        getHolder().addCallback(this);

    }

    public void StartThreads(){
        if (!sThread.running){
            sThread.setRunning(true);
            sThread.start();
        }

        System.out.println("Settings thread started.");
    }

    public void StopThreads(){
        while(true){
            try{
                sThread.setRunning(false);
                sThread.join();
                System.out.println("Settings thread stopped.");
                break;
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public void update(){
        bg.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        // Rect(int left, int top, int right, int bottom)

        bg.draw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        canvas.drawRect(rectShowButtonBorders, paint);
        if(blnShowBorders){
            canvas.drawLine(currentWidth / 16 , currentHeight / 16 * 3, currentWidth / 16 + 125, currentHeight / 16 * 3 + 125, paint);
            canvas.drawLine(currentWidth / 16 + 125, currentHeight / 16 * 3, currentWidth / 16, currentHeight / 16 * 3 + 125, paint);
        }
        canvas.drawRect(rectEnableSound, paint);
        if(blnEnableSound){
            canvas.drawLine(currentWidth / 16 , currentHeight / 16 * 5, currentWidth / 16 + 125, currentHeight / 16 * 5 + 125, paint);
            canvas.drawLine(currentWidth / 16 + 125, currentHeight / 16 * 5, currentWidth / 16, currentHeight / 16 * 5 + 125, paint);
        }
        canvas.drawRect(rectEnableMusic, paint);
        if(blnEnableMusic){
            canvas.drawLine(currentWidth / 16 , currentHeight / 16 * 7, currentWidth / 16 + 125, currentHeight / 16 * 7 + 125, paint);
            canvas.drawLine(currentWidth / 16 + 125, currentHeight / 16 * 7, currentWidth / 16, currentHeight / 16 * 7 + 125, paint);
        }

        canvas.drawRect(rectEnableBackground, paint);
        if(blnEnableBackground){
            canvas.drawLine(currentWidth / 16 , currentHeight / 16 * 9, currentWidth / 16 + 125, currentHeight / 16 * 9 + 125, paint);
            canvas.drawLine(currentWidth / 16 + 125, currentHeight / 16 * 9, currentWidth / 16, currentHeight / 16 * 9 + 125, paint);
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        canvas.drawText("Enable Buttons", (currentWidth) / 5, currentHeight / 32 * 7, paint);
        canvas.drawText("Enable Sound", (currentWidth) / 5, currentHeight / 32 * 11, paint);
        canvas.drawText("Render Music", (currentWidth) / 5, currentHeight / 32 * 15, paint);
        canvas.drawText("Render Background", (currentWidth) / 5, currentHeight / 32 * 19, paint);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

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
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
            if(event.getX() >= currentWidth / 16 && event.getX() <= currentWidth / 16 + 125){
                if((event.getY() >= currentHeight / 16 * 3 && event.getY() <= currentHeight / 16 * 3 + 125)){
                        blnShowBorders = !blnShowBorders;
                } else if((event.getY() >= currentHeight / 16 * 5 && event.getY() <= currentHeight / 16 * 5 + 125)){
                        blnEnableSound = !blnEnableSound;
                } else if((event.getY() >= currentHeight / 16 * 7 && event.getY() <= currentHeight / 16 * 7 + 125)){
                        blnEnableMusic = !blnEnableMusic;
                } else if((event.getY() >= currentHeight / 16 * 9 && event.getY() <= currentHeight / 16 * 9 + 125)){
                        blnEnableBackground = !blnEnableBackground;
                }
            }
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP){

        }

        return true;
    }
}
