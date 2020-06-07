package com.kelsiraman.peminum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static android.content.Context.ALARM_SERVICE;

public class Reminder extends BroadcastReceiver {
    private final int ID_REPEATING=101;
    @Override
    public void onReceive(Context context, Intent intent) {
        // cal masih kosongan
        // Calendar cal = Calendar.getInstance();
        Intent intent1 = new Intent(context, Notif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent1, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        // tunggu cal
        // alarm(am,pendingIntent,cal);

    }
    private void alarm(AlarmManager am,PendingIntent pendingIntent,Calendar calendar){
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d(TAG, "onReceive: panggil notif1");
        }
    }
}
