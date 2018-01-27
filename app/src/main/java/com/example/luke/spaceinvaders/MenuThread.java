package com.example.luke.spaceinvaders;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class MenuThread extends Thread
{
    private Menu menu;
    private SurfaceHolder surfaceHolder;
    public boolean running;
    public static Canvas canvas;

    public MenuThread(SurfaceHolder sHolder, Menu menu)
    {
        super();
        this.menu = menu;
        surfaceHolder = sHolder;
    }

    @Override
    public void run()
    {

        while (running)
        {
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.menu.draw(canvas);
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
        }
    }

    public void setRunning(boolean blnRunning){
        running = blnRunning;
    }
}
