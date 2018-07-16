package com.sapirye.brinksfall;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Level_One extends Activity {

    private GameView gameView;
    private BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level__one);
        Log.d("kkkkkkkkkk", "onCreate: *************");
        gameView= (GameView) findViewById(R.id.gameViewID);
        mReceiver = new MyReceiver();
        startGame();

    }

    private void startGame() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                gameView.animationLoop();
            }
        });
        thread.start();
        gameView.setEnabled(false);
    }

    protected void onStart()
    {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(mReceiver, intentFilter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }

}
