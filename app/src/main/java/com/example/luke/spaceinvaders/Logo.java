package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Luke on 13/07/2016.
 */
public class Logo extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap image;
    private Context context;
    private LogoThread lThread;
    private int screenWidth, screenHeight;

    public Logo(Context context, int w, int h){
        super(context);
        this.context = context;
        image = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        lThread = new LogoThread(getHolder(), this);
        lThread.setRunning(true);
        lThread.start();

        screenWidth = w;
        screenHeight = h;
    }

    public void StopThreads(){
        while(true){
            try{
                lThread.setRunning(false);
                System.out.println("Logo thread stopped.");
                break;
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public void update(){
        if(lThread.timePlayed >= 20){
            StopThreads();
            Intent intent = new Intent(context, MenuInit.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(image, screenWidth / 2 - image.getWidth() / 2, screenHeight / 2 - image.getHeight() / 2, null);
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
}
