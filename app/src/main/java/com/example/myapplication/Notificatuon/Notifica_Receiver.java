package com.example.myapplication.Notificatuon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Notifica_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("kakaa","chay service");
            Intent intent1= new Intent( context,myService.class );
            context.startService( intent1 );
    }
}
