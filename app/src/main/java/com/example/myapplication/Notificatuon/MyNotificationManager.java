package com.example.myapplication.Notificatuon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyNotificationManager {
    private Context mCtx;
    private static MyNotificationManager mInstance;

    public MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body, int icon) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx,"MyNotifications")
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(body);




        Intent resultIntent = new Intent(mCtx, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

        if (mNotifyMgr != null) {
            mNotifyMgr.notify(0, mBuilder.build());
        }
    }
    public void displayNotification1(String title, String body, int icon) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx,"MyNotifications")
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(body);




        Intent resultIntent = new Intent(mCtx, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }
    public void displayNotification2(String title, String body, int icon){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(mCtx,"MyNotifications")
                            .setSmallIcon(icon)
                            .setContentTitle(title)
                            .setContentText(body);
            Intent resultIntent = new Intent(mCtx, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            NotificationManager mNotifyMgr =
                    (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

            if (mNotifyMgr != null) {
                mNotifyMgr.notify(2, mBuilder.build());
            }
        }

    }



