package com.kelsiraman.peminum.mainlayout.home;

import android.content.Context;
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

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.home.recycleview.RecycleViewAdapter;
import com.kelsiraman.peminum.model.DataUser;
import com.kelsiraman.peminum.model.UpcomingModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String PARCEL = "DATAUSER";
    private RecycleViewAdapter adapter;
    private ArrayList<UpcomingModel> list = new ArrayList<>();
    private Context mContext;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        int banyakMenit = hitungBanyakMenit(parcelDU);
        double takaran = hitungTakaran(parcelDU);

        //TODO hapus method ini dan ganti dengan method mengisi data list
        SetDataKosong();
        setAdapter(view);
    }

    private void SetDataKosong() {
        UpcomingModel model = new UpcomingModel("1 gelas 240ml","10.20");
        for (int i = 0; i < 10; i++) {
            list.add(model);
        }
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
        takaran = Double.parseDouble(new DecimalFormat("#.##").format(takaran));
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
