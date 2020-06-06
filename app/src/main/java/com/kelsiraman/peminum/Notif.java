package com.kelsiraman.peminum;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kelsiraman.peminum.mainlayout.MainActivity;
import com.kelsiraman.peminum.pendaftaran.*;

import static android.content.ContentValues.TAG;

public class Notif extends BroadcastReceiver {
    private static final String CHANNEL_ID = "PEMINUM";
    private static final int NOTIFICATION_ID = 999;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: disini peminum");

        Intent intent2 = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent addPendingIntent = PendingIntent.getActivity(context,
                0, intent2, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MINUM WOY")
                .setContentText("200ML ya")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(addPendingIntent)
                .addAction(0, "Buka", addPendingIntent);



        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
