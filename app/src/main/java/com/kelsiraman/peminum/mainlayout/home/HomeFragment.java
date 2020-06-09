package com.kelsiraman.peminum.mainlayout.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.DataUser;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String PARCEL = "DATAUSER";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        DataUser parcelDU = getActivity().getIntent().getParcelableExtra(PARCEL);
        int banyakMenit = hitungBanyakMenit(parcelDU);
        double takaran = hitungTakaran(parcelDU);

        return view;
    }

    private double hitungTakaran(DataUser parcelDU) {
        double takaran = (((parcelDU.getUserBerat() * 2.205) * (2.0 / 3.0)) / 33.814) * 1000.0;
        takaran = Double.parseDouble(new DecimalFormat("#.##").format(takaran));
        Log.d("HITUNG", takaran + " ml");
        return takaran;
    }

    private int hitungBanyakMenit(DataUser parcelDU) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        int banyakMenit = 0;
        try {
            Date bangun = format.parse(parcelDU.getUserBangun());
            Date tidur = format.parse(parcelDU.getUserTidur());
            long durasi = tidur.getTime() - bangun.getTime();
            banyakMenit = (int) durasi / (60 * 1000) ;
            Log.d("HITUNG", banyakMenit + " minutes");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return banyakMenit;
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();

        return fragment;

    }
}
