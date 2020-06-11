package com.kelsiraman.peminum.mainlayout.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelsiraman.peminum.Notif;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.config.Konfigurasi;
import com.kelsiraman.peminum.mainlayout.home.recycleview.RecycleViewAdapter;
import com.kelsiraman.peminum.model.DataUser;
import com.kelsiraman.peminum.model.HistoryModel;
import com.kelsiraman.peminum.model.HistoryRvModel;
import com.kelsiraman.peminum.model.UpcomingModel;

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

    private ArrayList<String> alarm = new ArrayList<>();
    private ArrayList<String> historyTakaran = new ArrayList<>();
    private int biarInnitSekali = 0;
    private String historyTanggal;
    private int progress = 0;
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
        prepare(parcelDU);
        setAlarm(parcelDU);
        setAdapter(view);
        SharedPreferences sp = mContext.getSharedPreferences(Konfigurasi.PROGRESS, Context.MODE_PRIVATE);
        int amount= sp.getInt(Konfigurasi.AMOUNTPROGRESS,0);
        if (amount>0){
            akumulasi = Double.longBitsToDouble(sp.getLong(Konfigurasi.AKUMULASI, Double.doubleToLongBits(0.0)));
            String stringAkumulasi = String.valueOf(akumulasi);
            progressTakaran.setText(stringAkumulasi);
            progress = amount;
            arcProgress.setProgress(amount);
        }
        if (biarInnitSekali++ < 1) hitungWaktuMinum(parcelDU);
    }

    private void prepare(DataUser parcelDU) {
        arcProgress.setMax(10);
        String greeting = "Hai "+parcelDU.getUsername()+",\nSudahkah anda minum hari ini?";
        String max = "/"+hitungTakaran(parcelDU)+"ml";
        hello.setText(greeting);
        maxTakaran.setText(max);
        String stringSudahDiMinum = String.valueOf(akumulasi);
        progressTakaran.setText(stringSudahDiMinum);
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
        alarm.add(format.format(awalAlarm));
        for (int i = 1; i < 10; i++){
            cal.add(Calendar.MINUTE, (int) jedaMinum);
            alarm.add(format.format(cal.getTime()));
        }
        for (int i = progress; i < 10; i++) {
            UpcomingModel model = new UpcomingModel(sekaliMinum + " ml",alarm.get(i));
            historyTakaran.add(String.valueOf(sekaliMinum));
            list.add(model);
        }
    }

    private void pushHistoryToDB() {
        SharedPreferences sp = this.getActivity().getSharedPreferences(Konfigurasi.LOGINPREF,Context.MODE_PRIVATE);
        String getUID = sp.getString(Konfigurasi.UID,"undefined");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference root = ref.child("DataUser").child(getUID).child("History");
        String pushMinum = sekaliMinum + " ml";
        root.push().setValue(new HistoryModel(historyTanggal, pushMinum, "10:20"));
    }

    private void ambilHariTanggal() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", java.util.Locale.getDefault());
        String dayString = sdf.format(new Date());
        if (dayString.equals("Sunday")) dayString = "Minggu";
        if (dayString.equals("Monday")) dayString = "Senin";
        if (dayString.equals("Tuesday")) dayString = "Selasa";
        if (dayString.equals("Wednesday")) dayString = "Rabu";
        if (dayString.equals("Thursday")) dayString = "Kamis";
        if (dayString.equals("Friday")) dayString = "Jumat";
        if (dayString.equals("Saturday")) dayString = "Sabtu";
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
        String tanggal = sdf2.format(new Date());
        historyTanggal = dayString + ", " + tanggal;
        pushHistoryToDB();
    }

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.minumAir:
                if (arcProgress.getProgress()<10){
                    //TODO disable alarm disini
                    list.remove(0);
                    adapter.setUpcomingData(list,mContext);
                    arcProgress.setProgress(++progress);
                    akumulasi = akumulasi + sekaliMinum;
                    akumulasi = Math.floor(akumulasi * 10) / 10;
                    String stringSudahDiMinum = String.valueOf(akumulasi);
                    progressTakaran.setText(stringSudahDiMinum);
                    ambilHariTanggal();
                    SharedPreferences sp = mContext.getSharedPreferences(Konfigurasi.PROGRESS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sp.edit();
                    editor.putInt(Konfigurasi.AMOUNTPROGRESS, progress);
                    editor.putLong(Konfigurasi.AKUMULASI,Double.doubleToRawLongBits(akumulasi));
                    editor.apply();
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
