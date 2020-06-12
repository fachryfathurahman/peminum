package com.kelsiraman.peminum.mainlayout.expandHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.config.Konfigurasi;
import com.kelsiraman.peminum.mainlayout.expandHistory.recycleView.RecycleViewAdapter;
import com.kelsiraman.peminum.model.HistoryRvModel;

import java.util.ArrayList;

public class expandHistory extends AppCompatActivity {

    private RecyclerView rv;
    private RecycleViewAdapter adapter;
    private Context mContext;
    private HistoryRvModel model;
    private ArrayList<String> amount, time;
    private TextView titleDay;
    private ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_history);
        mContext = this;
        model = getIntent().getParcelableExtra(Konfigurasi.CDHISTORY);
        amount = model.getAmount();
        time = model.getTime();
        backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleDay = findViewById(R.id.textViewHari);
        titleDay.setText(model.getDay());
        setAdapter();
    }

    private void setAdapter() {
        RecyclerView rv = findViewById(R.id.rvExpanded);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RecycleViewAdapter();
        adapter.setExpandedData(amount, time,mContext);
        rv.setAdapter(adapter);
    }
}
