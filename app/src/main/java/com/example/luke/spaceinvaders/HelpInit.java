package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Luke on 20/06/2016.
 */
public class HelpInit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        setContentView(new GamePanel(this, width, height, "help"));
    }
}
