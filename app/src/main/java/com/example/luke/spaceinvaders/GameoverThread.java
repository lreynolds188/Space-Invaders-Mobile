package com.example.luke.spaceinvaders;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class GameoverThread extends Thread
{
    private Gameover gameover;
    private SurfaceHolder surfaceHolder;
    public boolean running;
    public static Canvas canvas;

    public GameoverThread(SurfaceHolder sHolder, Gameover gameover)
    {
        super();
        this.gameover = gameover;
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
                    this.gameover.draw(canvas);
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
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public void setRunning(boolean blnRunning){
        running = blnRunning;
    }
}
