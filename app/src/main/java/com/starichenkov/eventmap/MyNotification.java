package com.starichenkov.eventmap;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MyNotification {

    private Context mContext;
    private NotificationManager mManager;

    private String CHANNEL_ID;
    private String channel_name;
    private String channel_description;

    public MyNotification(Context context, String CHANNEL_ID, String channel_name, String channel_description){

        this.mContext = context;
        this.CHANNEL_ID = CHANNEL_ID;
        this.channel_name = channel_name;
        this.channel_description = channel_description;

        createChannel();
    }

    private void createChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = mContext.getString(R.string.channel_name);
            //String description = mContext.getString(R.string.channel_description);
            CharSequence name = channel_name;
            String description = channel_description;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            //NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            //notificationManager.createNotificationChannel(channel);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = mContext.getSystemService(NotificationManager.class);
        }
        return mManager;
    }

    public NotificationCompat.Builder getNotification(String title, String body) {
        return new NotificationCompat.Builder(mContext, CHANNEL_ID)
                //.setSmallIcon(R.drawable.event_map_logo_circle)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
    }

}
