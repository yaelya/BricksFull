package com.sapirye.brinksfall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class BrickCollection {

    //private static final float DY = 3;
    private static final String EASY = "easy";
    private static final String MEDIUM = "medium";
    private static final String ADVANCED = "advanced";
    private static final String HARD = "hard";


    private ArrayList<Brick> bricks;
    private Paint pen;
    private int x, y, w, h;
    private int dy;
    private String status;
    private Random r = new Random();
    private int num_bricks;
    private int rand;
    private float wScreen;
    private float hScreen;
    private int i ,j,gap, k=1,l2;
    private int status_level;



    public BrickCollection(String status, float wScreen, float hScreen,int status_level) {
        bricks = new ArrayList<>();
        this.status=status;
        this.wScreen=wScreen;
        this.hScreen=hScreen;
        this.status_level=status_level;
        x=0;
        gap=1;
        i=0;
        j=1;
        dy=1;
        l2=0;
        switch (status)
        {
            case EASY:
            {
                if(status_level==1)
                {
                    this.num_bricks=20;
                    j=5;
                }
                if(status_level==2)
                {
                    j=10;
                    this.num_bricks=20;
                    k=1;
                    rand=7;
                }
            }
            break;
            case MEDIUM:
            {
                l2=0;
                if(status_level==1)
                {
                    this.num_bricks=25;
                    j=10;
                }
                if(status_level==2)
                {
                    this.num_bricks=20;
                    k=1;
                    rand=7;
                    j=10;
                }
            }
            break;
            case ADVANCED:
            {
               if(status_level==1)
               {
                   this.num_bricks=30;
                   j=15;
                   dy=2;

               }
                if(status_level==2)
                {
                    this.num_bricks=25;
                    k=1;
                    dy=2;
                    j=15;
                    rand=6;
                }
            }
            break;
            case HARD:
            {
                if(status_level==1)
                {
                    this.num_bricks=35;
                    j=20;
                    dy=2;
                }
                if(status_level==2)
                {
                    this.num_bricks=30;
                    k=1;
                    dy=2;
                    j=20;
                    rand=3;
                }
            }
            break;
        }

        for (; i < num_bricks; i++) {
            k++;
            w = r.nextInt(450 - 150) + 150;
            h = r.nextInt(800 - 200) + 200;
            if((((int)wScreen/5)*gap+j+k)+w>=wScreen)
            {
                x=j;
                gap=1;
                k--;
                j--;
            }
            else
            {
                x= ((int)wScreen/5)*gap+j+k;
            }
            j+=25;
            if(j>100)
            {
                j=25;
            }
            y=-((int)hScreen/5)*k-j-l2;
            if(status_level==2)
            {
                l2+=(int)hScreen/rand;
            }
            int color = Color.argb(255, r.nextInt(255), r.nextInt(256), r.nextInt(255));
            bricks.add(new Brick(x, y, w, h, color, dy));
            gap++;
            if(gap>4) {
                gap=1;
            }
        }
    }

    public void draw(Canvas canvas) {
        bricks.get(0).draw(canvas);

        for (int i = 1; i < bricks.size(); i++) {
                bricks.get(i).draw(canvas);
                }

    }

    public void move(float width, float height) {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).move(width, height);
        }
    }

    public boolean check_come_floor(float height) {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).check_come_floor(height) == true) {
                return true;
            }
        }
        return false;
    }
    public void remove_brick(Brick b) {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i) == b) {
                bricks.remove(bricks.get(i));
            }
        }
    }

    public Brick check_start_contact(float x_start, float y_start){
        for(int i=0 ;i<bricks.size();i++) {
          if(x_start <= bricks.get(i).position_x() && x_start>=bricks.get(i).getX()
                  && y_start<=bricks.get(i).position_y() && y_start>=bricks.get(i).position_y()-150 )
          {
              return  bricks.get(i);
          }
        }
        return null;
    }
    public int check_move_contact(float x_move, float y_move, Brick b)
    {
        if(x_move >=b.getX() && x_move<=b.position_x() && y_move<b.position_y()&& y_move>b.getY())
            return 0;
       if(x_move>b.getX() && x_move<b.position_x() && y_move<b.position_y()&& y_move<=b.getY())
            return 1;
        return 2;
    }

    public boolean check_end_contact(float x_end, float y_end ,Brick b){
            if(x_end <= b.position_x() && x_end>=b.getX() && y_end<=b.getY()+15 && y_end> b.getY())
            {
                bricks.remove(b);
                return true;
            }
            return false;
    }
    public boolean check_player_brick(Player player)
    {
        for (int i=0;i<bricks.size();i++)
        {
            if (bricks.get(i).check_player_brick(player)==true)
             return true;
        }
        return false;
    }

    public int getNum_bricks(){
        return num_bricks;
    }

    public Brick check_come_floor_start(float height)
    {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).check_come_floor_start(height) == true) {
                return bricks.get(i);
            }
        }
        return null;
    }
}
