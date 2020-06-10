package com.kelsiraman.peminum.mainlayout.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
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


    private int progress;
    private int banyakMenit ;
    private double takaran ;
    private double sekaliMinum ;
    private double jedaMinum ;

    private double akumulasi = 0;

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
        hitungWaktuMinum(parcelDU);
        setAdapter(view);

        arcProgress = view.findViewById(R.id.arc_progress);
        btnTambahAir = view.findViewById(R.id.minumAir);
        btnTambahAir.setOnClickListener(this);
        maxTakaran = view.findViewById(R.id.takaranMax);
        progressTakaran = view.findViewById(R.id.progressTakaran);
        hello = view.findViewById(R.id.haiUser);

        try {
            prepare(parcelDU);
        }catch (Exception E){
            Toast.makeText(getContext(),"null",Toast.LENGTH_SHORT);
        }

        //todo ganti dengan progrees
        //warning : value ini akan kembali 0 jika di mulai app lagi
        progress = 1;

    }

    private void prepare(DataUser parcelDU) {
        arcProgress.setMax(10);
        hello.setText("Hai "+parcelDU.getUsername()+",\nSudahkah anda minum hari ini?");
        maxTakaran.setText("/"+Double.parseDouble(new DecimalFormat("#.##").format(hitungTakaran(parcelDU)))+"ml");
        progressTakaran.setText(akumulasi+"");

    }

    private void setAdapter(View view) {
        RecyclerView rv = view.findViewById(R.id.rvUpcoming);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RecycleViewAdapter();
        adapter.setUpcomingData(list,mContext);
        rv.setAdapter(adapter);
    }

    private double hitungTakaran(DataUser parcelDU) {
        double takaran = (((parcelDU.getUserBerat() * 2.205) * (2.0 / 3.0)) / 33.814) * 1000.0;
        takaran = Math.floor(takaran * 100) / 100;
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
        sekaliMinum = Math.floor(sekaliMinum * 100) / 100;
//        sekaliMinum = Double.parseDouble(new DecimalFormat("#.##").format(sekaliMinum));
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
                //todo tambahkan exception waktu supaya progress tida di tekan sembarangan, data minum belum masuk ke firebase
                if (arcProgress.getProgress()<10){
                    arcProgress.setProgress(progress++);
                    akumulasi = akumulasi + sekaliMinum;
                    progressTakaran.setText(Double.parseDouble(new DecimalFormat("#.##").format(akumulasi))+"");
                }else {
                    Toast.makeText(getContext(),"Jangan banyak banyak, kembung!",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}
