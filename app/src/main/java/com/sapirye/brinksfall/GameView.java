package com.sapirye.brinksfall;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    private static final int X_TEXT_SCORE = 20;
    private static final int X_TEXT_LIVES = 850;
    private static final int Y_TEXT = 90;
    private static final int POINTS = 5;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int LEVEL1 = 1;
    private static final int LEVEL2 = 2;
    private static final int GET_READY = 0;
    private static final int PLAYING = 1;
    private static final int GAME_OVER_LOSS = 2;
    private static final int GAME_OVER_WIN = 3;
    private static final int DX = 5;
    private static final int STEP = 15;
    private static final int STATUS_DOWN = 0;
    private static final int STATUS_UP = 1;


    private Player player;
    private Brick brick;
    private BrickCollection brickCollection;
    private Paint pen;
    private int score;
    private int lives;
    private String text;
    private float width, height;
    private int gameStatus;
    private int levelStatus;
    private int num_break_brick;
    private int position;
    private Bitmap bb;
    private int sum_move = 0;
    private float x_start, y_start, x_end, y_end, x_move, y_move;
    private boolean bb_position = true;
    //private float x_bb,y_bb;
    private boolean flag;
    private Brick b;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String status;
    private int player_status;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pen = new Paint();
        pen.setAntiAlias(true);
        sp = context.getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
        status = sp.getString("status", "easy");
        Log.d("33333333333", "tune1: uuuuuuuuuu");


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.argb(255, 255, 215, 0));
        pen.setColor(Color.BLACK);
        pen.setTextSize(60f);
        int i = 0;
        canvas.drawText("Score: " + score, X_TEXT_SCORE, Y_TEXT, pen);
        canvas.drawText("Lives: " + lives, width - 250, Y_TEXT, pen);
        pen.setColor(Color.BLACK);

        switch (levelStatus) {
            case LEVEL1: {
                if (gameStatus == GAME_OVER_LOSS) {
                    text = "GAME OVER You Loss!";
                    canvas.drawText(text, (width / 2) - 150, height / 2, pen);
                }
                if (gameStatus == GAME_OVER_WIN) {
                    text = "You Win! - click go to level 2";
                    canvas.drawText(text, (width / 2) - 300, height / 2, pen);
                }
                if (gameStatus == PLAYING) {
                        brickCollection.draw(canvas);
                    text = " ";
                    canvas.drawText(text, (width / 2) - 150, height / 2, pen);
                }
                if (gameStatus == GET_READY) {
                    text = "Click to PLAY!";
                    canvas.drawText(text, (width / 2) - 150, height / 2, pen);
                }
            }
            break;
            case LEVEL2: {
                if (gameStatus == GET_READY) {
                    text = "Click to PLAY!";
                    canvas.drawText(text, (width / 2) - 150, height / 2, pen);
                    Log.d("33333333333", "animationLoop:ggggggg   ");
                }
                if (gameStatus == PLAYING) {
                    text = " ";
                    brickCollection.draw(canvas);
                    canvas.drawText(text, (width / 2) - 150, height / 2, pen);
                    player.draw(canvas);
                }
                if (gameStatus == GAME_OVER_LOSS) {
                    text = "GAME OVER You Loss!";
                    canvas.drawText(text, (width / 2) - 200, height / 2, pen);

                }
                if (gameStatus == GAME_OVER_WIN) {
                    text = "You Win! - Click to PLAY agian";
                    canvas.drawText(text, (width / 2) - 350, height / 2, pen);

                }
            }
            break;
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initGame();
    }

    private void initGame() {
        brickCollection = new BrickCollection(status, width, height, levelStatus);
        score = 0;
        lives = 3;
        gameStatus = GET_READY;
        num_break_brick = 0;
        levelStatus = LEVEL1;
    }

    private void initGame2() {
        player = new Player(getContext(), width / 2, height - 600, width / 7, height / 7, STEP, width);
        score = 0;
        lives = 3;
        gameStatus = GET_READY;
        levelStatus = LEVEL2;
        brickCollection = new BrickCollection(status, width, height, levelStatus);
        Log.d("2222222", "animationLoop:initG2   " + brickCollection.getNum_bricks());
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (levelStatus) {
            case LEVEL1: {
                if (gameStatus == GET_READY) {
                    gameStatus = PLAYING;
                }
                if (gameStatus == GAME_OVER_LOSS) {
                    initGame();
                    gameStatus = GET_READY;
                }
                if (gameStatus == GAME_OVER_WIN) {
                    num_break_brick = 0;
                    initGame2();
                }
                if (gameStatus == PLAYING) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        x_start = event.getX();
                        y_start = event.getY();
                        b = brickCollection.check_start_contact(x_start, y_start);
                        if (b != null) {
                            flag = true;
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        x_move = event.getX();
                        y_move = event.getY();
                        if (b != null) {
                            if (brickCollection.check_move_contact(x_move, y_move, b) == 1 && flag == true) {
                                brickCollection.remove_brick(b);
                                num_break_brick++;
                                score += POINTS * lives * num_break_brick;
                                flag = false;
                            }
                            if (brickCollection.check_move_contact(x_move, y_move, b) == 2 && flag == true) {
                                flag = false;
                            }
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        x_end = event.getX();
                        y_end = event.getY();
                        if (b != null) {
                            if (brickCollection.check_end_contact(x_end, y_end, b) == true && flag == true)//15+up
                            {
                                num_break_brick++;
                                score += POINTS * lives * num_break_brick;
                                flag = false;
                            }
                        }
                    }
                }
            }
            break;
            case LEVEL2: {
                if (gameStatus == GET_READY) {
                    gameStatus = PLAYING;
                }
                if (gameStatus == PLAYING) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        position = touch_position(event.getX());
                        player_status = STATUS_DOWN;
                    } else
                        player_status = STATUS_UP;
                }

                if (gameStatus == GAME_OVER_LOSS) {
                    initGame2();
                }
                if (gameStatus == GAME_OVER_WIN) {
                    initGame2();
                }
            }
            break;
        }
        return true;
    }

    public void animationLoop() {
        while (true) {
            updateGame();
                //if(gameStatus==GAME_OVER_WIN && levelStatus==LEVEL1) {
                 //   initGame2();
                   // Log.d("44444", "animationLoop: hhhhhhhhhhhhhhh   ");
                //}
                   // break;
                invalidate();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        if (gameStatus == PLAYING) {
            palying();
        }

        switch (levelStatus) {
            case LEVEL1: {
                if (gameStatus == GET_READY) {
                    ready();
                }

            }
            break;
            case LEVEL2: {
                if (gameStatus == GET_READY) {
                    ready();
                }

                if (player_status == STATUS_DOWN) {
                    if (position == RIGHT)
                        player.moveRigth(position);
                    else if (position == LEFT)
                        player.moveLeft(position);
                }
            }
            break;
        }
    }

    private void ready() {
        brickCollection = new BrickCollection(status, width, height, levelStatus);
        text = "Click to PLAY!";
        if (levelStatus == LEVEL2) {
            player = new Player(getContext(), width / 2, height - 600, width / 7, height / 7, STEP, width);
        }
    }

    private void palying() {
        brickCollection.move(width, height);
        switch (levelStatus) {
            case LEVEL1: {
                if (brickCollection.check_come_floor(height) == true) {
                    lives--;
                    if (lives == 0) {
                        gameStatus = GAME_OVER_LOSS;
                        return;
                    }
                    gameStatus = GET_READY;
                    ready();
                }

                if (num_break_brick == brickCollection.getNum_bricks()) {
                    gameStatus = GAME_OVER_WIN;
                    num_break_brick = 0;
                }
            }
            break;
            case LEVEL2: {
                if (brickCollection.check_player_brick(player) == true) {

                    lives--;
                    if (lives == 0) {
                        gameStatus = GAME_OVER_LOSS;
                        return;
                    }
                    gameStatus = GET_READY;
                    ready();
                }

                Brick bbb = brickCollection.check_come_floor_start(height);
                if (bbb != null) {
                    brickCollection.remove_brick(bbb);
                    num_break_brick++;
                    score += POINTS * lives * num_break_brick;
                }
                if (num_break_brick == brickCollection.getNum_bricks()) {
                    Log.d("000000", "onTouchEvent: 555555");
                    gameStatus = GAME_OVER_WIN;
                    num_break_brick = 0;

                }

            }
            break;
        }
    }

    public int touch_position(float x) {
        if (x <= width / 2)
            return LEFT;
        else
            return RIGHT;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        gameStatus = GAME_OVER_WIN;
        Log.d("debug", "destroy view");

    }
}