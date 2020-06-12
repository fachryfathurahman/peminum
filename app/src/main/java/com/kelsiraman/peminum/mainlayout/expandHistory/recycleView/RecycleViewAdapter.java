package com.kelsiraman.peminum.mainlayout.expandHistory.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.HistoryRvModel;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HolderExpanded>{

    private Context mContext;
    private ArrayList<String> amount, time;

    public void setExpandedData(ArrayList<String> amount, ArrayList<String> time,Context context){
        this.amount = amount;
        this.time = time;
        this.mContext=context;
    }
    @NonNull
    @Override
    public HolderExpanded onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_history,parent,false);
        return new HolderExpanded(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderExpanded holder, int position) {
        String inAmount = amount.get(position);
        String inTime = time.get(position);
        holder.bind(inAmount, inTime);
    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class HolderExpanded extends RecyclerView.ViewHolder {
        TextView amount,time;
        public HolderExpanded(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amountHstr);
            time = itemView.findViewById(R.id.timeHstr);
        }
        public void bind(String inAmount, String inTime){

            amount.setText(inAmount);
            time.setText(inTime);
        }
    }
}
