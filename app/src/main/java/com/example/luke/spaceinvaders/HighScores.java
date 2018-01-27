package com.example.luke.spaceinvaders;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 9/07/2016.
 */
public class HighScores extends SurfaceView implements SurfaceHolder.Callback{

    int currentWidth, currentHeight;
    Paint paint = new Paint();
    Background bg;
    private HighScoresThread hsThread;
    private AssetManager am;
    private List<String[]> highScores;
    Context context;

    public HighScores(Context context, int screenWidth, int screenHeight){

        super(context);

        this.context = context;
        bg  = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        am = context.getAssets();
        currentHeight = screenHeight;
        currentWidth = screenWidth;
        highScores = Utils.LoadHighScores(context);

        hsThread = new HighScoresThread(getHolder(), this);
        StartThreads();

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setTypeface(Typeface.createFromAsset(am, "fonts/cosmicAlien.ttf"));
        paint.setTextSize(70);
        paint.setTextAlign(Paint.Align.CENTER);

        // enable access to the form
        setFocusable(true);

        // listen for user input events
        getHolder().addCallback(this);
    }

    public void StartThreads(){
        hsThread.setRunning(true);
        hsThread.start();
        System.out.println("Highscores thread started.");
    }

    public void StopThreads(){
        while(true){
            try{
                hsThread.setRunning(false);
                hsThread.join();
                System.out.println("Highscores thread stopped.");
                break;
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public void update(){
        bg.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        bg.draw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Top 10 High Scores", (currentWidth) / 2, currentHeight / 13 * 1, paint);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("1. " + highScores.get(0)[0] + " : " + highScores.get(0)[1], (currentWidth) / 8, currentHeight / 13 * 3, paint);
        canvas.drawText("2. " + highScores.get(1)[0] + " : " + highScores.get(1)[1], (currentWidth) / 8, currentHeight / 13 * 4, paint);
        canvas.drawText("3. " + highScores.get(2)[0] + " : " + highScores.get(2)[1], (currentWidth) / 8, currentHeight / 13 * 5, paint);
        canvas.drawText("4. " + highScores.get(3)[0] + " : " + highScores.get(3)[1], (currentWidth) / 8, currentHeight / 13 * 6, paint);
        canvas.drawText("5. " + highScores.get(4)[0] + " : " + highScores.get(4)[1], (currentWidth) / 8, currentHeight / 13 * 7, paint);
        canvas.drawText("6. " + highScores.get(5)[0] + " : " + highScores.get(5)[1], (currentWidth) / 8, currentHeight / 13 * 8, paint);
        canvas.drawText("7. " + highScores.get(6)[0] + " : " + highScores.get(6)[1], (currentWidth) / 8, currentHeight / 13 * 9, paint);
        canvas.drawText("8. " + highScores.get(7)[0] + " : " + highScores.get(7)[1], (currentWidth) / 8, currentHeight / 13 * 10, paint);
        canvas.drawText("9. " + highScores.get(8)[0] + " : " + highScores.get(8)[1], (currentWidth) / 8, currentHeight / 13 * 11, paint);
        canvas.drawText("10. " + highScores.get(9)[0] + " : " + highScores.get(9)[1], (currentWidth) / 8, currentHeight / 13 * 12, paint);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        StopThreads();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
            if(event.getX() >= currentWidth / 16 && event.getX() <= currentWidth / 16 + 125){

            }
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP){

        }

        return true;
    }
}
