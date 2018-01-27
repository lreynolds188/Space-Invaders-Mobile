package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class HighScoresInit extends Activity {

    Bundle savedInstance;
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstance = savedInstanceState;
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Toggle fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        setContentView(new HighScores(this, width, height));
    }

}
