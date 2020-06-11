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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

                HashMap<String, ArrayList<String>> hashMapAmount = new HashMap<>();
                HashMap<String, ArrayList<String>> hashMapTime = new HashMap<>();
                for (int i = 0; i < dataHistory.size(); i++) {
                    if (!hashMapAmount.containsKey(dataHistory.get(i).getDay())){
                        ArrayList<String> listAmount = new ArrayList<>();
                        listAmount.add(dataHistory.get(i).getAmount());
                        hashMapAmount.put(dataHistory.get(i).getDay(), listAmount);

                        ArrayList<String> listTime = new ArrayList<>();
                        listTime.add(dataHistory.get(i).getTime());
                        hashMapTime.put(dataHistory.get(i).getDay(),listTime);
                    }else {
                        hashMapAmount.get(dataHistory.get(i).getDay()).add(dataHistory.get(i).getAmount());
                        hashMapTime.get(dataHistory.get(i).getDay()).add(dataHistory.get(i).getTime());
                    }
                }

                ArrayList<String> day = new ArrayList<>();
                for (int i = 0; i < dataHistory.size(); i++) {
                    day.add(dataHistory.get(i).getDay());
                }

                Set<String> set = new HashSet<>(day);
                day.clear();
                day.addAll(set);

                for (String key: hashMapAmount.keySet()) {
                    Log.d("TAG", "onDataChange: "+key+" and "+hashMapAmount.get(key));
                }
                ArrayList<HistoryRvModel> data = new ArrayList<>();
                for (int i = 0; i < day.size(); i++) {
                    HistoryRvModel model = new HistoryRvModel();
                    String sDay = day.get(i);
                    model.setDay(sDay);
                    model.setAmount(hashMapAmount.get(day.get(i)));
                    model.setTime(hashMapTime.get(day.get(i)));
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
