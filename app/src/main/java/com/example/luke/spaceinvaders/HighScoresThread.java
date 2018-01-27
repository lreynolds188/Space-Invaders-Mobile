package com.example.luke.spaceinvaders;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Luke on 18/06/2016.
 */
public class HighScoresThread extends Thread
{
    private HighScores highScores;
    private SurfaceHolder surfaceHolder;
    public boolean running;
    public static Canvas canvas;

    public HighScoresThread(SurfaceHolder sHolder, HighScores highScores)
    {
        super();
        this.highScores = highScores;
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
                    this.highScores.update();
                    this.highScores.draw(canvas);
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
