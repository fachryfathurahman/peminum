package com.kelsiraman.peminum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kelsiraman.peminum.model.DataUser;

import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static android.content.Context.ALARM_SERVICE;

public class Reminder extends BroadcastReceiver {
    private final int ID_REPEATING=101;
    private static final String PARCEL = "DATAUSER";
    // j = jam, m = menit, t = tidur, b = bangun
    private int jt,mt,jb,mb;
    @Override
    public void onReceive(Context context, Intent intent) {
        DataUser parcelDU = intent.getParcelableExtra(PARCEL);
        String wt = parcelDU.getUserTidur();
        String wb = parcelDU.getUserBangun();
        String[] jmt = wt.split(":");
        String[] jmb = wb.split(":");
        jt = Integer.parseInt(jmt[0]);
        mt = Integer.parseInt(jmt[1]);
        jb = Integer.parseInt(jmb[0]);
        mb = Integer.parseInt(jmb[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        Intent intent1 = new Intent(context, Notif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent1, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //alarm diulang setiap 2.5 jam sekali
        alarm(am,pendingIntent,cal);

    }
    private void alarm(AlarmManager am,PendingIntent pendingIntent,Calendar calendar){
        if (am != null) {
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 * 150, pendingIntent);
            //am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d(TAG, "onReceive: panggil notif 2.5 jam");
        }
    }
}
