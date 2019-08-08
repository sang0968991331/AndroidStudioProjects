package com.example.myapplication.Notificatuon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class myService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private Context mCtx;
    private static myService mInstance;

    private myService(Context context) {
        mCtx = context;
    }

    public static synchronized myService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new myService(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        Log.e("lala", "vao ser");
        //createNotificationChannel();
       // Intent notificationIntent = new Intent(this, MainActivity.class);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this,
         //       0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx,"MyNotifications")
                        .setSmallIcon( R.drawable.ic_launcher_background)
                        .setContentTitle("sang")
                        .setContentText(input);



        Intent resultIntent = new Intent(mCtx, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }

        //do heavy work on a background thread


        //stopSelf();

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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService( NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}