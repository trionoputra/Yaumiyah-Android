package com.yondev.yaumiyah.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ThinkPad on 5/10/2017.
 */

public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context.getApplicationContext(), SchedulerService.class);
        context.startService(service);
        Log.d("YAUMIYAH","start service again");
    }
}