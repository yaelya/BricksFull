package com.sapirye.brinksfall;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String EASY = "easy";
    private static final String MEDIUM = "medium";
    private static final String ADVANCED = "advanced";
    private static final String HARD = "hard";

    private Button btn1,btn2,btn3,btn4;
    public static final int RequestPermissionCode  = 2;
    private TextView mBatteryLevelText;
    private Intent in;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBatteryLevelText = (TextView) findViewById(R.id.textView);
        in = new Intent(MainActivity.this, MyService.class);
        sp = getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
        editor = sp.edit();
        btn1 = (Button) findViewById(R.id.btn1ID);
        btn2 = (Button) findViewById(R.id.btn2ID);
        btn3 = (Button) findViewById(R.id.btn3ID);
        btn4 = (Button) findViewById(R.id.btn4ID);

        status = sp.getString("status", "easy");
        switch (status) {
            case EASY: {
                in.putExtra("status",EASY);
                editor.putString("status",EASY);
                editor.commit();
                btn1.setEnabled(false);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn4.setEnabled(true);
            }
            break;

            case MEDIUM: {
                in.putExtra("status",MEDIUM);
                editor.putString("status",MEDIUM);
                editor.commit();
                btn1.setEnabled(true);
                btn2.setEnabled(false);
                btn3.setEnabled(true);
                btn4.setEnabled(true);
            }
            break;
            case ADVANCED:
            {
                in.putExtra("status",ADVANCED);
                editor.putString("status",ADVANCED);
                editor.commit();
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(false);
                btn4.setEnabled(true);
            }
            break;
            case HARD:
            {
                in.putExtra("status",HARD);
                editor.putString("status",HARD);
                editor.commit();
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn4.setEnabled(false);
            }
            break;
        }
    }

    public void start(View view) {
        Intent t = new Intent(this,Level_One.class);
        startActivity(t);

        this.startService(in);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(in);
    }

    public void Instructions(View view)
    {
        Intent t = new Intent(this,Instructions.class);
        startActivity(t);
    }
    public void tune1(View view)
    {
        if (status!=null){
            onDestroy();
        }
        in.putExtra("status",EASY);
        editor.putString("status",EASY);
        editor.commit();
        btn1.setEnabled(false);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);

    }
    public void tune2(View view)
    {
        if (status!=null){
            onDestroy();
        }
       in.putExtra("status",MEDIUM);
        editor.putString("status",MEDIUM);
        editor.commit();
        btn2.setEnabled(false);
        btn1.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
    }
    public void tune3(View view)
    {
        if (status!=null){
            onDestroy();
        }
        in.putExtra("status",ADVANCED);
        editor.putString("status",ADVANCED);
        editor.commit();
        btn3.setEnabled(false);
        btn2.setEnabled(true);
        btn1.setEnabled(true);
        btn4.setEnabled(true);
    }
     public void tune4(View view)
    {
        if (status!=null){
            onDestroy();
        }
        in.putExtra("status",HARD);
        editor.putString("status",HARD);
        editor.commit();
        btn4.setEnabled(false);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn1.setEnabled(true);
    }
}
