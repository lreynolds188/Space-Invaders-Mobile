package com.example.luke.spaceinvaders;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Luke on 8/07/2016.
 */
public class Help extends Activity {

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

    int sixthOfScreenHeight, fourthOfScreenWidth, currentWidth, currentHeight;
    Paint tempPaint = new Paint();

    public Help(int screenWidth, int screenHeight, Typeface cosmicAlien){

        tempPaint.setColor(Color.RED);
        tempPaint.setTypeface(cosmicAlien);
        tempPaint.setTextSize(45);
        tempPaint.setTextAlign(Paint.Align.CENTER);
        sixthOfScreenHeight = screenHeight / 6;
        fourthOfScreenWidth = screenWidth / 4;
        currentHeight = screenHeight;
        currentWidth = screenWidth;
    }

    public void draw(Canvas canvas){

        // left / top / right / bottom
        // Shoot
        tempPaint.setStyle(Paint.Style.STROKE);
        tempPaint.setStrokeWidth(10);
        canvas.drawRect((float)0, (float) sixthOfScreenHeight *4,(float)currentWidth, (float) sixthOfScreenHeight *5, tempPaint);
        // Left and Right
        canvas.drawRect((float)currentWidth/2, (float) sixthOfScreenHeight *5,(float)currentWidth, (float) sixthOfScreenHeight *6, tempPaint);
        canvas.drawRect((float)0, (float) sixthOfScreenHeight *5,(float)currentWidth, (float) sixthOfScreenHeight *6, tempPaint);

        // Shoot Text
        tempPaint.setStyle(Paint.Style.FILL);
        tempPaint.setStrokeWidth(1);
        canvas.drawText("SHOOT", (float)currentWidth/2, (float)(sixthOfScreenHeight *4)+(sixthOfScreenHeight /2), tempPaint);

        // Left and Right Text
        canvas.drawText("MOVE LEFT", (float)fourthOfScreenWidth, (float)(sixthOfScreenHeight *5)+(sixthOfScreenHeight /2), tempPaint);
        canvas.drawText("MOVE RIGHT", (float)fourthOfScreenWidth*3, (float)(sixthOfScreenHeight *5)+(sixthOfScreenHeight /2), tempPaint);
    }
}
