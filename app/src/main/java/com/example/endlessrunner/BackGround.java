package com.example.endlessrunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BackGround {
    Bitmap background, ground;
    Rect rectBackground, rectGround;

    public BackGround(Context context, int dWidth, int dHeight){
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky);
        ground = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);

        rectBackground = new Rect(0, 0, dWidth, dHeight);
        rectGround = new Rect(0, dHeight * 3 / 4, dWidth, dHeight);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
    }
}
