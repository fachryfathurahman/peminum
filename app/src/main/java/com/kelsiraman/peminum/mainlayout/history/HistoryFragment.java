package com.kelsiraman.peminum.mainlayout.history;

import android.content.Context;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.config.Konfigurasi;
import com.kelsiraman.peminum.mainlayout.history.recycleView.RecycleViewAdapter;
import com.kelsiraman.peminum.model.HistoryModel;
import com.kelsiraman.peminum.model.HistoryRvModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private RecycleViewAdapter adapter;
    private RecyclerView rv;
    private ArrayList<HistoryModel> dataHistory;
    private Context mContext;
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        rv = view.findViewById(R.id.rvHistory);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        SharedPreferences sp = this.getActivity().getSharedPreferences(Konfigurasi.LOGINPREF,Context.MODE_PRIVATE);
        String getUID = sp.getString(Konfigurasi.UID,"undefined");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference root = ref.child("DataUser").child(getUID).child("History");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataHistory = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HistoryModel hModel = ds.getValue(HistoryModel.class);
                    dataHistory.add(hModel);
                }
                ArrayList<String> day = new ArrayList<>();
                ArrayList<String> amount = new ArrayList<>();
                ArrayList<String> time = new ArrayList<>();
                for (int i = 0; i < dataHistory.size(); i++) {
                    day.add(dataHistory.get(i).getDay());
                    amount.add(dataHistory.get(i).getAmount());
                    time.add(dataHistory.get(i).getTime());
                }

                String tmpDay = day.get(0);
                ArrayList<String> amountGroup = new ArrayList<>();
                ArrayList<String> timeGroup = new ArrayList<>();
                ArrayList<ArrayList<String>> groupAmount = new ArrayList<>();
                ArrayList<ArrayList<String>> groupTime = new ArrayList<>();
                int totalGroup = 0;
                for (int i = 0; i < day.size(); i++) {
                    if (day.get(i).equals(tmpDay)){
                        amountGroup.add(amount.get(i));
                        timeGroup.add(time.get(i));
                    }else {
                        groupAmount.add(amountGroup);
                        Log.d("TAG", "onDataChange: "+amountGroup.get(i));
                        groupTime.add(timeGroup);
                        amountGroup.clear();
                        timeGroup.clear();
                        tmpDay = day.get(i);
                        i--;
                        totalGroup++;
                    }
                    if (i == day.size() - 1){
                        groupAmount.add(amountGroup);
                        Log.d("TAG", "onDataChange: "+amountGroup.get(i));
                        groupTime.add(timeGroup);
                        totalGroup++;
                    }
                }
                ArrayList<HistoryRvModel> data = new ArrayList<>();
                for (int i = 0; i <totalGroup; i++) {
                    HistoryRvModel model = new HistoryRvModel();
                    model.setDay(day.get(i));
                    model.setAmount(groupAmount.get(i));
                    model.setTime(groupTime.get(i));
                    data.add(model);
                }

                adapter = new RecycleViewAdapter();
                adapter.setHistoryData(data,mContext);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DBERROR", databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }

//    private void setDataKosong() {
//        HistoryModel model = new HistoryModel("Minggu, 10-10-2010","200 ml", "10:20");
//        list.add(model);
//    }

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
