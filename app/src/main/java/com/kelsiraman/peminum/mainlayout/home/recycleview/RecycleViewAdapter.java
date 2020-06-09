package com.kelsiraman.peminum.mainlayout.home.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.UpcomingModel;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.Holder> {
    private ArrayList<UpcomingModel> upcomingData;
    private Context mContext;

    public void setUpcomingData(ArrayList<UpcomingModel> upcomingData, Context mContext){
        this.upcomingData = upcomingData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.Holder holder, int position) {
        UpcomingModel model = upcomingData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return upcomingData.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView upcomingAmount, upcomingTime;
        public Holder(@NonNull View itemView) {
            super(itemView);
            upcomingAmount= itemView.findViewById(R.id.upcomingAmount);
            upcomingTime = itemView.findViewById(R.id.upcomingTime);
        }

        public void bind(UpcomingModel model) {
            upcomingAmount.setText(model.getAmount());
            upcomingTime.setText(model.getTime());
        }
    }
}
