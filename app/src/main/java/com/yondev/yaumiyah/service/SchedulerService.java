package com.yondev.yaumiyah.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.yondev.yaumiyah.MainActivity;
import com.yondev.yaumiyah.R;
import com.yondev.yaumiyah.entity.Target;
import com.yondev.yaumiyah.sqlite.DatabaseHelper;
import com.yondev.yaumiyah.sqlite.DatabaseManager;
import com.yondev.yaumiyah.sqlite.DbSchema;
import com.yondev.yaumiyah.sqlite.ds.TargetDataSource;
import com.yondev.yaumiyah.utils.Shared;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SchedulerService extends Service {

    public boolean isSchedulerRunning = false;
    private mThreadNOTIF mthreadNotif;
    private String TAG = "YAUMIYAH";
    public SchedulerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        Shared.initialize(getApplicationContext());
        DatabaseManager.initializeInstance(new DatabaseHelper(getApplicationContext()));

        mthreadNotif = new mThreadNOTIF();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
       Log.d(TAG,"onStartCommand");
        if(!isSchedulerRunning){
            mthreadNotif.start();
            isSchedulerRunning = true;
            Log.d(TAG,"startTread");
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG,"onDestroy");
       if(!isSchedulerRunning)
        {
            mthreadNotif.interrupt();
            mthreadNotif.stop();
            Log.d(TAG,"stopTread");
        }

        sendBroadcast(new Intent("YouWillNeverKillMe"));

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG,"onTaksRemove");
        sendBroadcast(new Intent("YouWillNeverKillMe"));
    }

    private void doIng()
    {
        ArrayList<Target> data = new ArrayList<>();


        ArrayList<String> filter = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        filter.add(" " + DbSchema.COL_TARGET_NOTIFICATION + " = 1 " );

        String ft = " (" + DbSchema.COL_TARGET_PENGULANGAN + " = 1 and " + DbSchema.COL_TARGET_LAST_NOTIF + " IS NULL AND " + DbSchema.COL_TARGET_WAKTU + " <= '"+ Shared.dateformat.format(new Date()) +"' and DATETIME(" + DbSchema.COL_TARGET_WAKTU + ", '+5 minutes')  >= '"+ Shared.dateformat.format(new Date()) +"' )";
        ft += " OR (" + DbSchema.COL_TARGET_PENGULANGAN + " = 2 and ( " + DbSchema.COL_TARGET_LAST_NOTIF + " IS NULL OR strftime('%Y-%m-%d 00:01'," + DbSchema.COL_TARGET_LAST_NOTIF + ") <  strftime('%Y-%m-%d 00:01','" + Shared.dateformatDate.format(new Date()) + "') ) AND strftime('%H:%M'," + DbSchema.COL_TARGET_WAKTU + ") <= '"+ Shared.dateformatTime.format(new Date()) +"' and strftime('%H:%M',DATETIME(" + DbSchema.COL_TARGET_WAKTU + ", '+5 minutes'))   >= '"+ Shared.dateformatTime.format(new Date()) +"' )";
        ft += " OR (" + DbSchema.COL_TARGET_PENGULANGAN + " = 3 and ( " + DbSchema.COL_TARGET_LAST_NOTIF + " IS NULL OR strftime('%w'," + DbSchema.COL_TARGET_WAKTU + ") =  strftime('%w','"+Shared.dateformat.format(new Date())+"') ) AND strftime('%H:%M'," + DbSchema.COL_TARGET_WAKTU + ") <= '"+ Shared.dateformatTime.format(new Date()) +"' and strftime('%H:%M',DATETIME(" + DbSchema.COL_TARGET_WAKTU + ", '+5 minutes'))   >= '"+ Shared.dateformatTime.format(new Date()) +"' )";
        ft += " OR (" + DbSchema.COL_TARGET_PENGULANGAN + " = 4 and ( " + DbSchema.COL_TARGET_LAST_NOTIF + " IS NULL OR strftime('%d'," + DbSchema.COL_TARGET_WAKTU + ") =  strftime('%d','"+Shared.dateformat.format(new Date())+"') ) AND strftime('%H:%M'," + DbSchema.COL_TARGET_WAKTU + ") <= '"+ Shared.dateformatTime.format(new Date()) +"' and strftime('%H:%M',DATETIME(" + DbSchema.COL_TARGET_WAKTU + ", '+5 minutes'))   >= '"+ Shared.dateformatTime.format(new Date()) +"' )";
        ft += " OR (" + DbSchema.COL_TARGET_PENGULANGAN + " = 5 and ( " + DbSchema.COL_TARGET_LAST_NOTIF + " IS NULL OR strftime('%m-%d'," + DbSchema.COL_TARGET_WAKTU + ") =  strftime('%m-%d','"+Shared.dateformat.format(new Date())+"') ) AND strftime('%H:%M'," + DbSchema.COL_TARGET_WAKTU + ") <= '"+ Shared.dateformatTime.format(new Date()) +"' and strftime('%H:%M',DATETIME(" + DbSchema.COL_TARGET_WAKTU + ", '+5 minutes'))   >= '"+ Shared.dateformatTime.format(new Date()) +"' )";

        filter.add(ft);
        
        SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
        TargetDataSource DS = new TargetDataSource(db);
        data = DS.getAll(filter,null,0,0);
        DatabaseManager.getInstance().closeDatabase();

        Log.d("YAUMIYAH",data.size() + "");

        int id = 0;
        for (Target target : data)
        {
            if(target.isNotifikasi())
            {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent contentIntent = PendingIntent.getActivity(this, uniqueInt,intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle(getString(R.string.salam));
                mBuilder.setContentText(target.getJudul());
                mBuilder.setContentIntent(contentIntent);
                mBuilder.setLights(Color.GREEN, 500, 500);
                mBuilder.setSound(Uri.parse(target.getSounduri()));

                if(target.isVibrasi())
                    mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

                notificationManager.notify(id, mBuilder.build());

                SQLiteDatabase db2 =  DatabaseManager.getInstance().openDatabase();
                TargetDataSource DS2 = new TargetDataSource(db2);
                target.setLast_notif(new Date());
                DS2.update(target,target.getId());
                DatabaseManager.getInstance().closeDatabase();

                id ++;
            }
        }
    }
    class mThreadNOTIF extends Thread{
        final long DELAY = 5000;
        @Override
        public void run(){
            while(isSchedulerRunning){
                try
                {
                    doIng();
                    Log.d(TAG,"DoinDoin");
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    isSchedulerRunning = false;
                    Log.d(TAG,"Doin ERROR");
                    e.printStackTrace();

                }
            }
        }
    }
}
