package com.example.endlessrunner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.app.Activity;
import android.icu.text.SymbolTable;

public class Player {
    private int playerX, playerY, playerWidth, playerHeight;
    private int velocityX, velocityY, gravity;
    private int frame, elapsed;
    private int score;
    private int textSize;

    private boolean playing;

    private Bitmap[] run;
    private Bitmap jump;
    private Bitmap restartButton, exitButton;

    private Rect playerRect;
    private Rect restartButtonRect;
    private Rect exitButtonRect;

    Paint textPaint = new Paint();

    Enemies enemies;
    
    public Player(Context context, int dWidth, int dHeight){
        enemies = new Enemies(context, dWidth, dHeight);

        textSize = 120;

        playing = true;

        velocityX = 10;
        velocityY = 0;
        gravity = 1;
        playerWidth = 128;
        playerHeight = 168;

        playerX = dWidth / 4 - playerWidth / 2;
        playerY = dHeight * 3/4 - playerHeight;

        playerRect = new Rect(playerX, playerY,playerX + playerWidth,playerY + playerHeight);

        restartButtonRect = new Rect(dWidth / 4, dHeight / 4, dWidth * 3/ 4, dHeight / 2);
        exitButtonRect = new Rect(dWidth / 4, dHeight / 2, dWidth * 3/ 4, dHeight * 3/ 4);

        int runIds[] = {R.drawable.player_walk_1, R.drawable.player_walk_2};
        int jumpId = R.drawable.jump;

        int restartButtonId = R.drawable.restartbutton;
        int exitButtonId = R.drawable.exitbutton;

        run = new Bitmap[runIds.length];
        for (int i=0; i<runIds.length; i++){
            run[i] = BitmapFactory.decodeResource(context.getResources(), runIds[i]);
        }

        jump = BitmapFactory.decodeResource(context.getResources(), jumpId);

        restartButton = BitmapFactory.decodeResource(context.getResources(), restartButtonId);
        exitButton = BitmapFactory.decodeResource(context.getResources(), exitButtonId);

        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    public void draw(Canvas canvas){
        canvas.drawText("" + score, 20, textSize, textPaint);

        if (playing) {
            if (velocityY == 0) {
                canvas.drawBitmap(run[frame], null, playerRect, null);
            } else if (velocityY != 0) {
                canvas.drawBitmap(jump, null, playerRect, null);
            }

            enemies.draw(canvas);
        }
    }

    public void update(Context context, int dWidth, int dHeight){
        if (playing) {
            score++;
            enemies.update(dWidth);
            updateFrames();
            enemyCollision(context);
            verticalMovement();
            groundCollision(dHeight);
        }
    }

    public void setJump(){
        if (playing){
            if (velocityY == 0){
                velocityY = -25;
            }
        }
    }

    public void updateFrames(){
        elapsed++;
        if (velocityY == 0){
            if (elapsed % 10 == 0){
                frame = (frame + 1) % run.length;
            }
        }
    }

    public void verticalMovement(){
        velocityY += gravity;
        playerY += velocityY;

        playerRect.top = playerY;
        playerRect.bottom = playerY + playerHeight;
    }

    public void groundCollision(int dHeight){
        if (playerY + playerHeight >= dHeight * 3/4){
            velocityY = 0;
            playerY = dHeight * 3/4 - playerHeight - 1;
        }
    }

    public void enemyCollision(Context context){
        if (playerX <= enemies.getSnailX() + enemies.getSnailWidth() && playerX + playerWidth >= enemies.getSnailX() && playerY + playerHeight >= enemies.getSnailY() && playerY <= enemies.getSnailY() + enemies.getSnailHeight()){
            playing = false;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score", score);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        if (playerX <= enemies.getFlyX() + enemies.getFlyWidth() && playerX + playerWidth >= enemies.getFlyX() && playerY + playerHeight >= enemies.getFlyY() && playerY <= enemies.getFlyY() + enemies.getFlyHeight()){
            playing = false;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score", score);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }

    public boolean getPlaying(){
        return playing;
    }
}

