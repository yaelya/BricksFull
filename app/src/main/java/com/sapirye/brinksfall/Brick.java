package com.sapirye.brinksfall;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Brick {
        private float x,y;
        private float w,h,dy;
        private int color;
        private Paint pen;

        public Brick(float x, float y,float w, float h,int color,float dy )
        {
            this.x = x;
            this.y = y;
            this.w= w;
            this.h= h;
            this.color = color;
            this.dy=dy;
            pen = new Paint();
        }

        public void draw(Canvas canvas)
        {
            pen.setColor(color);
            canvas.drawRect(x,y,x+w,y+h,pen);
        }

       public void move(float width, float height)
        {
            y=y+dy;
           if (y + h < height) {
               y += dy;
           }
        }
    public boolean check_come_floor(float height)
    {
        if (y+h >= height) {
            return true;
        }
        return false;
    }
    public boolean check_contact_brick(float x_tuch, float y_tuch){
            if(y_tuch<=position_y()&& y_tuch>=position_y()/3 && x_tuch<=position_x()&& x_tuch>x)
            {
                return  true;
            }
            return false;
    }
    public float position_x()
        {
          return x+w;
        }
    public float position_y()
        {
            return y+h;
        }
    public float position_m()
    {
        return y+h/2;
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
    public void setDy(float y)
        {
            this.dy=y;
        }
    public float getW()
        {
            return w;
        }
    public float getH()
        {
            return h;
        }

    public boolean check_player_brick(Player player)
    {
       float x_new,y_new,w_new,h_new;
       x_new=x-player.getW();
       y_new=y-player.getH();
       w_new=w+((player.getW())*2);
       h_new=h+((player.getH())*2);
      if ((player.getX()>=x_new) &&  (player.getX()<=x_new+w) && player.position_x()>=x &&
              player.position_x()<=x_new+w_new && (player.getY()>=y_new) &&
              (player.getY()<=y_new+h && player.position_y()>=y && player.position_y()<=y_new+h_new) ) {
          return true;
        }
        return false;
    }


    public boolean check_come_floor_start(float height)
    {
        if (y>= height) {
            return true;
        }
        return false;
    }
    }


