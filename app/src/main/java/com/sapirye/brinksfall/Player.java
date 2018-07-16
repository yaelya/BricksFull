package com.sapirye.brinksfall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Player {
private float x,y;
private float w,h,wScreen;
private int step;
private Bitmap player;
private Paint pen;


    public Player(Context context,float x, float y , float w, float h,int step,float wScreen) {
        this.x = x;
        this.y=y;
        this.h=h;
        this.w=w;
        this.wScreen=wScreen;
        this.step=step;
        pen = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bb);
        player = Bitmap.createScaledBitmap(bitmap, (int)x, (int)y, false);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(player,x,y,null);
    }

    public float getX()
    {
        return x;
    }
    public void setY(float y)
    {
        this.y=y;
    }
    public void setW(float w)
    {
        this.w=w;
    }
    public void setH(float h)
    {
        this.h=h;
    }
    public float getY()
    {
        return y;
    }
    public float getW()
    {
        return w;
    }
    public float getH()
    {
        return h;
    }
    public float position_y(){
        return y+h;
    }
    public float position_x(){
        return x+w;
    }

    public void moveRigth(int position){
        if (x+w+step<=wScreen )
            x=x+step;
        else {
            x =wScreen-w-step;
        }
    }
    public void moveLeft(int position)
    {
        if (x-step>0)
            x=x-step;
        else
            x=0-w-step;
    }



}
