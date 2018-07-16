package com.sapirye.brinksfall;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class    MyService extends Service {
    private static final String EASY = "easy";
    private static final String MEDIUM = "medium";
    private static final String ADVANCED = "advanced";
    private static final String HARD = "hard";


    private String status;
    private MediaPlayer player;
    private MainActivity m;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        status = intent.getStringExtra("status");
       switch (status) {
            case EASY: {
                player = MediaPlayer.create(this, R.raw.easy);
                if (player.isPlaying()) {
                    player.pause();
                }
            }
            break;
            case MEDIUM: {
                player = MediaPlayer.create(this, R.raw.maidum);
                if (player.isPlaying()) {
                    player.pause();
                }
            }
            break;
            case ADVANCED: {
                player = MediaPlayer.create(this, R.raw.hard);
                if (player.isPlaying()) {
                    player.pause();
                }
            }
            break;
            case HARD: {
                player = MediaPlayer.create(this, R.raw.ttt);
                if (player.isPlaying()) {
                    player.pause();
                }
            }
            break;
       }
         player.setLooping(true);
         player.start();
         return START_STICKY;
        }



        @Override
        public void onDestroy ()
        {
            super.onDestroy();
            player.stop();
        }

    }
