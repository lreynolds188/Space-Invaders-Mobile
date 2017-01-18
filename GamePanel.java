package com.example.luke.spaceinvaders;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Luke on 18/06/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    // Global Classes
    private GameThread gameThread;
    private CollisionThread collisionThread;
    private Background background;
    private Player player;
    private Help help;
    private Gameover gameover;
    private static Lives lives;
    private static Level level;
    private static Score score;

    // Global Variables
    private Context context;
    private static int screenWidth = -1,
                       screenHeight = -1,
                       wavShoot = -1,
                       wavInvaderKilled = -1,
                       wavExplosion = -1;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Barrier> barriers;
    private float enemyXvel = 0, enemyYvel = 0;
    private Typeface cosmicAlien;
    private AssetManager am;
    private static SoundPool soundPool;
    private Bitmap bmpPlayer, bmpEnemy1, bmpEnemy2, bmpEnemy3, bmpEnemyShip;

    public static boolean bulletCharge = true;
    public static String gamestate = "playing";


    public GamePanel(Context context, int w, int h, String gamestate)
    {
        // what does this do????
        super(context);

        this.context = context;
        this.gamestate = gamestate;
        screenWidth = w;
        screenHeight = h;
        am = getContext().getAssets();
        cosmicAlien = Typeface.createFromAsset(am, "fonts/cosmicAlien.ttf");
        bmpEnemy1 = BitmapFactory.decodeResource(getResources(), R.drawable.enemy1);
        bmpEnemy2 = BitmapFactory.decodeResource(getResources(), R.drawable.enemy2);
        bmpEnemy3 = BitmapFactory.decodeResource(getResources(), R.drawable.enemy3);
        bmpEnemyShip = BitmapFactory.decodeResource(getResources(), R.drawable.enemyship);
        bmpPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        LoadSoundEffect();
        score = new Score(screenWidth, cosmicAlien);
        level = new Level(screenWidth, cosmicAlien);

        gameThread = new GameThread(getHolder(), this);
        collisionThread = new CollisionThread(this);
        help = new Help(screenWidth, screenHeight, cosmicAlien);

        // enable access to the form
        setFocusable(true);

        // listen for user input events
        getHolder().addCallback(this);

        System.out.println("Creating new game.");
        newGame(true);

        StartThreads();
    }

    public void newGame(boolean resetScore)
    {
        gameThread.timePlayed = 0;
        // create the background
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));

        enemies = new ArrayList<>();
        bullets =  new ArrayList<>();
        barriers =  new ArrayList<>();

        lives = new Lives(screenWidth, cosmicAlien, BitmapFactory.decodeResource(getResources(), R.drawable.playerlife));
        if(resetScore){
            score.update(0);
            level.update(1);
        }


        for(int j = 0; j < 4; j++){
            barriers.add(new Barrier(130 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 32, UpdateBarrierImages(0)));
            barriers.add(new Barrier(226 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 32, UpdateBarrierImages(1)));
            barriers.add(new Barrier(130 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 96, UpdateBarrierImages(2)));
            barriers.add(new Barrier(226 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 96, UpdateBarrierImages(3)));
        }

        for(int j = 0; j < 4; j++){
            barriers.add(new Barrier(162 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 32, UpdateBarrierImages(4)));
            barriers.add(new Barrier(194 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 32, UpdateBarrierImages(4)));
            barriers.add(new Barrier(130 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 64, UpdateBarrierImages(4)));
            barriers.add(new Barrier(162 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 64, UpdateBarrierImages(4)));
            barriers.add(new Barrier(194 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 64, UpdateBarrierImages(4)));
            barriers.add(new Barrier(226 + (j * 350), (screenHeight /6)*4 + (screenHeight /24) + 64, UpdateBarrierImages(4)));
        }

        CreateEnemies();

        player = new Player(bmpPlayer, (screenWidth / 2) - (bmpPlayer.getWidth() / 2), screenHeight, screenWidth);

        System.out.println("New game created.");

    }

    public void CreateEnemies(){

        int x = 0, startX = 0, y = 0;
        startX = -5;
        y = 0;
        if(gamestate == "help") {
            startX = 155;
            y = 400;
        }

        // enemy 1 Stronger
        for (int i = 0; i < 20; i++)
        {
            x = i;
            if(x >= 10){
                x = i - 10;
                y = 100;
                if(gamestate == "help"){
                    y = 500;

                }
            }
            enemies.add(new Enemy(bmpEnemy1, startX + (x * 120), 100 + y, 1));
        }

        // enemy 2
        startX = -15;
        y = 0;
        if(gamestate == "help"){
            startX = 145;
            y = 400;
        }

        for (int i = 0; i < 20; i++)
        {
            x = i;
            if(x >= 10){
                x = i - 10;
                y = 100;
                if(gamestate == "help"){
                    y = 500;
                }
            }
            enemies.add(new Enemy(bmpEnemy2, startX + (x * 120), 300 + y, 2));
        }

        // enemy 3 Weaker
        startX = -20;
        y = 0;
        if(gamestate == "help"){
            startX = 140;
            y = 400;
        }

        for (int i = 0; i < 20; i++)
        {
            x = i;
            if(x >= 10){
                x = i-10;
                y = 100;
                if(gamestate == "help"){
                    y = 500;
                }
            }
            enemies.add(new Enemy(bmpEnemy3, startX + (x * 120), 500 + y, 3));
        }

    }

    // barrierType = 0:TopLeft 1:TopRight 2:BottomLeft 3:BottomRight 4:Center
    public ArrayList<Bitmap> UpdateBarrierImages(int barrierType) {
        ArrayList<Bitmap> barrierImages = new ArrayList<>();
        if (barrierType == 0) {
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.topleftbarrier3));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.topleftbarrier2));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.topleftbarrier));
        } else if (barrierType == 1) {
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.toprightbarrier3));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.toprightbarrier2));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.toprightbarrier));
        } else if (barrierType == 2) {
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.bottomleftbarrier3));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.bottomleftbarrier2));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.bottomleftbarrier));
        } else if (barrierType == 3) {
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.bottomrightbarrier3));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.bottomrightbarrier2));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.bottomrightbarrier));
        } else if (barrierType == 4) {
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.barrier3));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.barrier2));
            barrierImages.add(BitmapFactory.decodeResource(getResources(), R.drawable.barrier));
        }
        return barrierImages;
    }

    public void StartThreads(){
        if (!gameThread.running){
            gameThread.setRunning(true);
            gameThread.start();
        } else {
            gameThread.timePlayed = 0;
        }

        if (!collisionThread.running){
            collisionThread.setRunning(true);
            collisionThread.start();
        }
        System.out.println("Game threads started.");
    }

    public void StopThreads(){
        StopGameThread();
        StopCollisionThread();
    }

    public void StopGameThread() {
        try {
            gameThread.setRunning(false);
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void StopCollisionThread(){
        try {
            collisionThread.setRunning(false);
            collisionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void LoadSoundEffect(){
        try{
            AssetFileDescriptor descriptor;

            descriptor = am.openFd("shoot.wav");
            wavShoot = soundPool.load(descriptor, 0);

            descriptor = am.openFd("invaderkilled.wav");
            wavInvaderKilled = soundPool.load(descriptor, 0);

            descriptor = am.openFd("explosion.wav");
            wavExplosion = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            System.out.println("Failed to load sound file");
        }
    }

    public static void UpdateScore(int points){
        score.update(points);
        soundPool.play(wavInvaderKilled, 1, 1, 0, 0, 1);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        StopThreads();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gamestate == "playing" || gamestate == "help") {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                if (event.getX() <= screenWidth / 2 && event.getY() >= (screenHeight / 6) * 5) {
                    player.setVelocity(-15);
                }

                if (event.getX() >= screenWidth / 2 && event.getY() >= (screenHeight / 6) * 5) {
                    player.setVelocity(15);
                }

                if (event.getY() <= (screenHeight / 5) * 4 && event.getY() >= (screenHeight / 6) * 4) {
                    if (bulletCharge) {
                        bulletCharge = false;
                        soundPool.play(wavShoot, 1, 1, 0, 0, 1);
                        int[] playerCoords = player.returnCoords();
                        bullets.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.playerbullet), playerCoords[0] + 87, playerCoords[1] + 20));
                    }
                }
            }

            if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                player.setVelocity(0);
            }
        } else if (gamestate == "gameover") {
            if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
                if((event.getY() > screenHeight/6*4 && event.getY() < screenHeight/6*4 + screenHeight/24*3)){
                    gameover.newGameDown = true;
                } else if (event.getY() > screenHeight/6*5 && event.getY() < screenHeight/6*5 + screenHeight/24*3){
                    gameover.menuDown = true;
                }
            } else if(event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                gameover.newGameDown = false;
                gameover.menuDown = false;

                if (event.getX() > screenWidth/5 && event.getX() < screenWidth/5*4) {
                    if (event.getY() > screenHeight / 6 * 4 && event.getY() < screenHeight / 6 * 4 + screenHeight / 24 * 3) {
                        newGame(true);
                    } else if (event.getY() > screenHeight / 6 * 5 && event.getY() < screenHeight / 6 * 5 + screenHeight / 24 * 3) {
                        Intent i = new Intent(context, MenuInit.class);
                        context.startActivity(i);
                    }
                }
            }
        }
        return true;
    }

    public void update()
    {
        background.update();
        if(gamestate == "playing" || gamestate == "help")
        {
            if (enemies.size() <= 0) {
                level.level++;
                newGame(false);
            }

            Utils.LoadBullet(enemies);

            player.update();

            for (Bullet bullet : bullets){
                bullet.update();
            }

            if(gamestate == "playing"){

                enemyXvel = Utils.updateEnemyAxis(enemies, level.level)[0];
                enemyYvel = Utils.updateEnemyAxis(enemies, level.level)[1];


                for (Enemy enemy : enemies)
                {
                    enemy.setVelocity(enemyXvel, enemyYvel);
                }
                for (Enemy enemy : enemies)
                {
                    enemy.update();
                }


            } else {
                if (enemies.size() <= 0) {
                    newGame(true);
                }

            }
        } else if(gamestate == "gameover"){

        } else if(gamestate == "initGameover"){
            gameover = new Gameover(screenWidth, screenHeight, score.score, cosmicAlien);
            gamestate = "gameover";

            System.out.println("Game threads stopped.");
            soundPool.play(wavExplosion, 1, 1, 0, 0, 1);
        }
    }

    public void CollisionUpdate(){
        Utils.CheckCollisions(bullets, enemies, barriers, player);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if(gamestate == "playing" || gamestate == "help")
        {
            background.draw(canvas);
            if (bullets != null)
            {
                for (Bullet bullet : bullets){
                    bullet.draw(canvas);
                }
            }

            if (barriers != null)
            {
                for (Barrier barrier : barriers){
                    barrier.draw(canvas);
                }
            }

            player.draw(canvas);

            lives.draw(canvas);

            level.draw(canvas);

            score.draw(canvas);

            if (enemies != null)
            {
                for (Enemy enemy : enemies)
                {
                    enemy.draw(canvas);
                }
            }

            if(gamestate == "help"){
                help.draw(canvas);
            }

        } else if(gamestate == "gameover"){
            gameover.draw(canvas);
        }
    }
}
