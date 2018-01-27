package com.example.luke.spaceinvaders;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class SettingsThread extends Thread
{
    private Settings settings;
    private SurfaceHolder surfaceHolder;
    public boolean running;
    public static Canvas canvas;

    public SettingsThread(SurfaceHolder sHolder, Settings settings)
    {
        super();
        this.settings = settings;
        surfaceHolder = sHolder;
    }

    @Override
    public void run()
    {

        while (running)
        {
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder){
                    this.settings.update();
                    this.settings.draw(canvas);
                }
            }catch(Exception e){
            }
            finally{
                if(canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){
                    }
                }
            }
        }
    }

    public void setRunning(boolean blnRunning){
        running = blnRunning;
    }
}
