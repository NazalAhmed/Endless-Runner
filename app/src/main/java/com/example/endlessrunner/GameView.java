package com.example.endlessrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.Display;
import android.app.Activity;
import android.graphics.Point;

import android.os.Handler;

public class GameView extends View {
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 10;
    Runnable runnable;

    int dWidth, dHeight;

    Player player;
    BackGround backGround;

    public GameView(Context context) {
        super(context);
        this.context = context;

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        player = new Player(context, dWidth, dHeight);
        backGround = new BackGround(context, dWidth, dHeight);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        backGround.draw(canvas);

        player.update(context, dWidth, dHeight);
        player.draw(canvas);

        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        float x = event.getX();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (player.getPlaying()) {
                player.setJump();
            }
        }

        return true;
    }
}
