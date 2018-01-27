package com.example.luke.spaceinvaders;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Luke on 18/06/2016.
 */
public class LogoThread extends Thread
{
    private int fps = 30;
    private Logo logo;
    private SurfaceHolder surfaceHolder;
    public boolean running;
    public float timePlayed = 0;
    public float difference = 1;
    public static Canvas canvas;

    public LogoThread(SurfaceHolder sHolder, Logo logo)
    {
        super();
        this.logo = logo;
        surfaceHolder = sHolder;
    }

    @Override
    public void run()
    {
        int frameCount = 0;

        while (running)
        {

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.logo.update();
                    this.logo.draw(canvas);
                }
            }catch(Exception e){}
            finally{
                if(canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}

                }
            }
            frameCount++;

            timePlayed += difference / 3;

            if(frameCount == fps){
                frameCount = 0;
            }
        }
    }

    public void setRunning(boolean blnRunning){
        running = blnRunning;
    }
}
