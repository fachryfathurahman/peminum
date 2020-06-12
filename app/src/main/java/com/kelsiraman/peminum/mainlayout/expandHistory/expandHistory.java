package com.kelsiraman.peminum.mainlayout.expandHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.expandHistory.recycleView.RecycleViewAdapter;

import java.util.ArrayList;

public class expandHistory extends AppCompatActivity {

    private RecyclerView rv;
    private RecycleViewAdapter adapter;
    private ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_history);

    }
}
