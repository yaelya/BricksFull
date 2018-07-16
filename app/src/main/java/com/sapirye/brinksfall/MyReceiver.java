package com.sapirye.brinksfall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        if (level<12) {
            Toast.makeText(context, "Battery is low", Toast.LENGTH_SHORT).show();
            Log.d("debug", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + intent.ACTION_BATTERY_CHANGED+ "    "+level);
        }

    }
}
