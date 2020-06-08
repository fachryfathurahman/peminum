package com.kelsiraman.peminum.mainlayout.history;

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

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.history.recycleView.RecycleViewAdapter;
import com.kelsiraman.peminum.model.HistoryModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private RecycleViewAdapter adapter;
    private ArrayList<HistoryModel> list = new ArrayList<>();
    private Context mContext;
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();

        //TODO hapus method ini jika data sudah di implementasikan
        setDataKosong();

        RecyclerView rv = view.findViewById(R.id.rvHistory);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new RecycleViewAdapter();
        adapter.setHistoryData(list,mContext);
        rv.setAdapter(adapter);
    }

    private void setDataKosong() {

        ArrayList<String> time = new ArrayList<>(Arrays.asList("10.20", "11.20","11.40"));
        ArrayList<String> amount = new ArrayList<>(Arrays.asList("1 gelas 200 ml","1 gelas 400 ml","1 gelas 200 ml"));
        HistoryModel model = new HistoryModel("minggu",amount, time);
        list.add(model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    public static HistoryFragment newInstance(){
        HistoryFragment fragment = new HistoryFragment();

        return fragment;

    }
}
