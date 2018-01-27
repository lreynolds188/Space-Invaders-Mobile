package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class GameInit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Toggle fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        setContentView(new GamePanel(this, width, height, "playing"));
    }
}
