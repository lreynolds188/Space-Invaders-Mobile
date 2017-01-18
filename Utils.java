package com.example.luke.spaceinvaders;

import android.content.Context;
import android.os.Environment;
import android.widget.TableLayout;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 10/07/2016.
 */
public class Utils {

    public static int[] updateEnemyAxis(ArrayList<Enemy> enemies, int level)
    {
        int[] tempAry = new int[2];

        int i = 0;

        while(enemies.get(i) == null){i++;}

        Enemy tempEnemy = enemies.get(i);

        if(level == 1) {
            if (tempEnemy.updateCount <= 4) {
                tempAry[0] = 50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 5) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            } else if (tempEnemy.updateCount <= 9) {
                tempAry[0] = -50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 10) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            }
        } else if (level == 2){
            if (tempEnemy.updateCount <= 4) {
                tempAry[0] = 50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 5) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            } else if (tempEnemy.updateCount <= 9) {
                tempAry[0] = -50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 10) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            }
        }else if (level == 3){
            if (tempEnemy.updateCount <= 4) {
                tempAry[0] = 50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 5) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            } else if (tempEnemy.updateCount <= 9) {
                tempAry[0] = -50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 10) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            }
        }else if (level == 4){
            if (tempEnemy.updateCount <= 4) {
                tempAry[0] = 50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 5) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            } else if (tempEnemy.updateCount <= 9) {
                tempAry[0] = -50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 10) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            }
        } else if (level == 5){
            if (tempEnemy.updateCount <= 4) {
                tempAry[0] = 50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 5) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            } else if (tempEnemy.updateCount <= 9) {
                tempAry[0] = -50;
                tempAry[1] = 0;
            } else if (tempEnemy.updateCount <= 10) {
                tempAry[0] = 0;
                tempAry[1] = 50;
            }
        }




        if (tempEnemy.updateCount > 10){
            for(Enemy enemy : enemies){
                enemy.updateCount = 1;
            }
        }

        return tempAry;
    }

    public static void LoadBullet(ArrayList<Enemy> enemies)
    {
        int i = 0;

        while(enemies.get(i) == null){i++;}

        Enemy tempEnemy = enemies.get(i);

        if(tempEnemy.updateCheck % 10 == 0) {
            GamePanel.bulletCharge = true;
        }
    }

    public static void UpdateScore(Enemy enemy){
        switch ((int)enemy.type){
            case 1 : GamePanel.UpdateScore(20);
                break;
            case 2 : GamePanel.UpdateScore(10);
                break;
            case 3 : GamePanel.UpdateScore(5);
                break;
        }
    }

    public static void CheckCollisions(ArrayList<Bullet> bullets, ArrayList<Enemy> enemies, ArrayList<Barrier> barriers, Player player){

        for (Bullet bullet : bullets){

            if(bullet.y <= -10){
                bullets.remove(bullet);
            }

            for(Enemy enemy : enemies){
                if(CheckCollision(bullet.hitbox(), enemy.hitbox()))
                {
                    bullets.remove(bullet);
                    enemies.remove(enemy);
                    Utils.UpdateScore(enemy);
                }
            }

            for(Barrier barrier : barriers){
                if(CheckCollision(bullet.hitbox(), barrier.hitbox())){
                    bullets.remove(bullet);
                    if(barrier.lives <= 1){
                        barriers.remove(barrier);
                    } else{
                        barrier.lives--;
                    }

                }
            }
        }

        for(Enemy enemy : enemies){
            if(CheckCollision(enemy.hitbox(), player.hitbox())){GamePanel.gamestate = "initGameover";}

            for(Barrier barrier : barriers){
                if(CheckCollision(enemy.hitbox(), barrier.hitbox())){
                    GamePanel.gamestate = "initGameover";
                }
            }
        }

    }

    // hitbox = x, y, width, height
    public static boolean CheckCollision(float[] hitbox1, float[] hitbox2)
    {
        return  hitbox1[0] < hitbox2[0] + hitbox2[2] &&
                hitbox1[0] + hitbox1[2] > hitbox2[0] &&
                hitbox1[1] < hitbox2[1] + hitbox2[3] &&
                hitbox2[1] < hitbox1[1] + hitbox1[3];
    }

    public static List<String[]> LoadConfig(Context context){

        CSVReader csvReader;
        List<String[]> config = new ArrayList<>();

        try{
            InputStream in;
            in = context.getAssets().open("config.csv");
            InputStreamReader reader = new InputStreamReader(in);
            csvReader = new CSVReader(reader);
            String[] line;

            //Throw away the heading
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null){
                config.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to load config.");
        }

        return config;
    }

    public static List<String[]> LoadHighScores(Context context){

        CSVReader csvReader;
        List<String[]> highScores = new ArrayList<>();

        try{
            InputStream in;
            in = context.getAssets().open("highscores.csv");
            InputStreamReader reader = new InputStreamReader(in);
            csvReader = new CSVReader(reader);
            String[] line;

            //Throw away the heading
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null){
                highScores.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to load high scores.");
        }

        return highScores;
    }

}
