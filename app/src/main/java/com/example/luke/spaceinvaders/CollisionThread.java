package com.example.luke.spaceinvaders;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class CollisionThread extends Thread
{
    private GamePanel gamePanel;
    public boolean running;
    public long waitTime;

    public CollisionThread(GamePanel gamePanel)
    {
        super();
        this.gamePanel = gamePanel;
    }

    @Override
    public void run()
    {
        while (running)
        {
            try{
                this.gamePanel.CollisionUpdate();
            }catch(Exception e){}

        }
    }

    public void setRunning(boolean blnRunning){
        running = blnRunning;
    }
}
