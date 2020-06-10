package com.kelsiraman.peminum.mainlayout.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.kelsiraman.peminum.Notif;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.home.recycleview.RecycleViewAdapter;
import com.kelsiraman.peminum.model.DataUser;
import com.kelsiraman.peminum.model.UpcomingModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String PARCEL = "DATAUSER";
    private Date awalAlarm, akhirAlarm;
    private SimpleDateFormat format;
    private RecycleViewAdapter adapter;
    private ArrayList<UpcomingModel> list = new ArrayList<>();
    private Context mContext;
    private Button btnTambahAir;
    private TextView hello;
    private ArcProgress arcProgress;
    private TextView maxTakaran;
    private TextView progressTakaran;

    private int biarInnitSekali = 0;

    private int progress;
    private int banyakMenit ;
    private double takaran ;
    private double sekaliMinum ;
    private double jedaMinum ;

    private double akumulasi = 0;

    private final int ID_REPEATING=101;
    private Intent intent1;
    private PendingIntent pendingIntent;
    private AlarmManager am;
    // j = jam, m = menit, t = tidur, b = bangun
    private int jt,mt,jb,mb;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        DataUser parcelDU = getActivity().getIntent().getParcelableExtra(PARCEL);
        format = new SimpleDateFormat("HH:mm");
        arcProgress = view.findViewById(R.id.arc_progress);
        maxTakaran = view.findViewById(R.id.takaranMax);
        progressTakaran = view.findViewById(R.id.progressTakaran);
        hello = view.findViewById(R.id.haiUser);
        btnTambahAir = view.findViewById(R.id.minumAir);
        btnTambahAir.setOnClickListener(this);
        setAdapter(view);
        if (biarInnitSekali < 1) {
            hitungWaktuMinum(parcelDU);
            biarInnitSekali++;
        }
        prepare(parcelDU);
        setAlarm(parcelDU);

        //todo ganti dengan progrees
        //warning : value ini akan kembali 0 jika di mulai app lagi
        progress = 1;

    }

    private void prepare(DataUser parcelDU) {
        arcProgress.setMax(10);
        String greeting = "Hai "+parcelDU.getUsername()+",\nSudahkah anda minum hari ini?";
        String max = "/"+hitungTakaran(parcelDU)+"ml";
        hello.setText(greeting);
        maxTakaran.setText(max);
        String akumulus = String.valueOf(akumulasi);
        progressTakaran.setText(akumulus);

    }

    private void setAdapter(View view) {
        RecyclerView rv = view.findViewById(R.id.rvUpcoming);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RecycleViewAdapter();
        adapter.setUpcomingData(list,mContext);
        rv.setAdapter(adapter);
    }

    private double hitungTakaran(DataUser parcelDU) {
        takaran = (((parcelDU.getUserBerat() * 2.205) * (2.0 / 3.0)) / 33.814) * 1000.0;
        takaran = Math.floor(takaran);
        return takaran;
    }

    private int hitungBanyakMenit(DataUser parcelDU) {
        int banyakMenit = 0;
        try {
            Date bangun = format.parse(parcelDU.getUserBangun());
            Date tidur = format.parse(parcelDU.getUserTidur());
            long durasi = tidur.getTime() - bangun.getTime();
            banyakMenit = (int) durasi / (60 * 1000) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return banyakMenit;
    }

    private void hitungWaktuMinum(DataUser parcelDU) {
        banyakMenit = hitungBanyakMenit(parcelDU);
        takaran = hitungTakaran(parcelDU);
        sekaliMinum = takaran / 10.0;
        jedaMinum = banyakMenit / 10.0;
        sekaliMinum = Math.floor(sekaliMinum * 10) / 10;
        try {
            awalAlarm = format.parse(parcelDU.getUserBangun());
            akhirAlarm = format.parse(parcelDU.getUserTidur());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(awalAlarm);
        String[] alarm = new String[10];
        alarm[0] = format.format(awalAlarm);
        for (int i = 1; i < 10; i++){
            cal.add(Calendar.MINUTE, (int) jedaMinum);
            alarm[i] = format.format(cal.getTime());
        }
        for (int i = 0; i < 10; i++) {
            UpcomingModel model = new UpcomingModel(sekaliMinum + " ml",alarm[i]);
            list.add(model);
        }
    }

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.minumAir:
                if (arcProgress.getProgress()<10){
                    arcProgress.setProgress(progress++);
                    akumulasi = akumulasi + sekaliMinum;
                    akumulasi = Math.floor(akumulasi * 10) / 10;
                    String stringAkumulasi = String.valueOf(akumulasi);
                    progressTakaran.setText(stringAkumulasi);
                }else {
                    Toast.makeText(getContext(),"Jangan banyak banyak, kembung!",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private void setAlarm(DataUser parcelDU) {
        String wt = parcelDU.getUserTidur();
        String wb = parcelDU.getUserBangun();
        String[] jmt = wt.split(":");
        String[] jmb = wb.split(":");
        jt = Integer.parseInt(jmt[0]);
        mt = Integer.parseInt(jmt[1]);
        jb = Integer.parseInt(jmb[0]);
        mb = Integer.parseInt(jmb[1]);

        intent1 = new Intent(getActivity(), Notif.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), ID_REPEATING,intent1, 0);
        am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        alarmBangun(jb,mb);
        alarmKedua(jb,mb);
        alarmKetiga(jb,mb);
        alarmKeempat(jb,mb);
        alarmKelima(jb,mb);
        alarmKeenam(jb,mb);
        alarmKetujuh(jb,mb);
        alarmTidur(jt,mt);
    }
    private void alarmBangun(int jb, int mb){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        if (am != null) {
            //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 150, pendingIntent);
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 1");
        }
    }
    private void alarmKedua(int jb, int mb){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis()+150*1000), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 2");
        }
    }
    private void alarmKetiga(int jb, int mb){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis()+300*1000), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 3");
        }
    }
    private void alarmKeempat(int jb, int mb){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis()+450*1000), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 4");
        }
    }
    private void alarmKelima(int jb, int mb){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis()+600*1000), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 5");
        }
    }
    private void alarmKeenam(int jb, int mb){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis()+750*1000), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 6");
        }
    }
    private void alarmKetujuh(int jb, int mb){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jb);
        cal.set(Calendar.MINUTE, mb);
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis()+900*1000), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 7");
        }
    }
    private void alarmTidur(int jt, int mt){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, jt);
        cal.set(Calendar.MINUTE, mt);
        if (am != null) {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("this", "onReceive: notif 8");
        }
    }
}
