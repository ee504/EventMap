package com.starichenkov.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.eventmap.R;

import static com.starichenkov.app.MyApp.CHANNEL_ID;

public class ExampleService extends Service {

    public static final String nameService = "Event notification";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String input = intent.getStringExtra(nameService);

        Intent notificationIntent = new Intent(this, MainMapActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startServise(Context context, String input){
        Intent serviceIntent = new Intent(context, ExampleService.class);
        serviceIntent.putExtra(nameService, input);

        startService(serviceIntent);
    }

    public void stopServise(Context context){
        Intent serviceIntent = new Intent(context, ExampleService.class);

        stopService(serviceIntent);
    }
}
