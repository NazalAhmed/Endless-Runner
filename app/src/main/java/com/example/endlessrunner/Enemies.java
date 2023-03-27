package com.example.endlessrunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Enemies {
    private int snailX, snailY, flyX, flyY;
    private int flyWidth, flyHeight, snailWidth, snailHeight;
    private int frame;
    private int snailVelocity, flyVelocity, speedChanger;

    Random random;

    private Rect snailRect, flyRect;

    private Bitmap[] snail, fly;

    public Enemies(Context context, int dWidth, int dHeight){
        snailHeight = 72;
        flyWidth = 168;
        flyHeight = 80;
        snailWidth = 144;

        snailVelocity = 9;
        flyVelocity = 10;
        speedChanger = 0;

        random = new Random();

        snailX = random.nextInt((dWidth + dWidth) - (dWidth + dWidth/4) + 1) + (dWidth + dWidth/4);
        flyX = random.nextInt((dWidth/2 - dWidth/4) + 1) + (dWidth/4 + dWidth);

        snailY = dHeight * 3/4 - snailHeight;
        flyY = dHeight * 1/2 - flyHeight;

        snailRect = new Rect(snailX, snailY, snailX + snailWidth, snailY + snailHeight);
        flyRect = new Rect(flyX, flyY, flyX + flyWidth, flyY + flyHeight);

        int snailIds[] = {R.drawable.snail0, R.drawable.snail1};
        int flyIds[] = {R.drawable.fly0, R.drawable.fly1};

        snail = new Bitmap[snailIds.length];
        for (int i=0; i<snailIds.length; i++){
            snail[i] = BitmapFactory.decodeResource(context.getResources(), snailIds[i]);
        }

        fly = new Bitmap[flyIds.length];
        for (int i=0; i<flyIds.length; i++){
            fly[i] = BitmapFactory.decodeResource(context.getResources(), flyIds[i]);
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(snail[frame], null, snailRect, null);
        canvas.drawBitmap(fly[frame], null, flyRect, null);
    }

    public void update(int dWidth){
        changeFrames();
        move();
        resetPosition(dWidth);
    }

    public void move(){
        snailX -= snailVelocity;
        flyX -= flyVelocity;

        snailRect.left = snailX;
        snailRect.right = snailX + snailWidth;

        flyRect.left = flyX;
        flyRect.right = flyX + flyWidth;

        speedChanger++;
        if (speedChanger % 10000 == 0 && snailVelocity < 30 && flyVelocity < 30){
            snailVelocity += 1;
            flyVelocity += 1;
        }

        if (snailVelocity >= 30 || flyVelocity >= 30){
            snailVelocity = 30;
            flyVelocity = 30;
        }
    }

    public void changeFrames(){
        frame = (frame + 1) % 2;
    }

    public void resetPosition(int dWidth){
        if (snailX <= -100){
            snailX = random.nextInt((dWidth + dWidth) - (dWidth + dWidth/4) + 1) + (dWidth + dWidth/4);
        }
        if (flyX <= -200){
            flyX = random.nextInt((dWidth/2 - dWidth/4) + 1) + (dWidth/4 + dWidth);
        }
    }

    public int getSnailX(){
        return snailX;
    }
    public int getSnailY(){
        return snailY;
    }
    public int getFlyX(){
        return flyX;
    }
    public int getFlyY(){
        return flyY;
    }
    public int getSnailWidth(){
        return snailWidth;
    }
    public int getSnailHeight(){
        return snailHeight;
    }
    public int getFlyWidth(){
        return flyWidth;
    }
    public int getFlyHeight(){
        return flyHeight;
    }
}
