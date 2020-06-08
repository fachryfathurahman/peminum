package com.kelsiraman.peminum.mainlayout.history.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.HistoryModel;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HolderHistory> {
    private ArrayList<HistoryModel> historyData;
    private Context mContext;

    public void setHistoryData(ArrayList<HistoryModel> historyData, Context mContext){
        this.historyData = historyData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);

        return new HolderHistory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderHistory holder, int position) {
        HistoryModel model = historyData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class HolderHistory extends RecyclerView.ViewHolder {
        TextView historyDay, historyAmount,historyTime;
        TextView historyAmount2,historyTime2;
        TextView historyAmount3, historyTime3;
        public HolderHistory(@NonNull View itemView) {
            super(itemView);
            historyDay = itemView.findViewById(R.id.historyDay);
            historyAmount = itemView.findViewById(R.id.historyAmount);
            historyTime= itemView.findViewById(R.id.historyTime);
            historyAmount2 = itemView.findViewById(R.id.historyAmount2);
            historyTime2 = itemView.findViewById(R.id.historyTime2);
            historyAmount3 = itemView.findViewById(R.id.historyAmount3);
            historyTime3 = itemView.findViewById(R.id.historyTime3);
        }

        public void bind(HistoryModel model) {
            int size = model.getAmount().size();
            historyDay.setText(model.getDay());
            historyAmount.setText(model.getAmount().get(size-1));
            historyTime.setText(model.getTime().get(size-1));
            if (size>2){
                historyAmount2.setText(model.getAmount().get(size-2));
                historyTime2.setText(model.getTime().get(size-2));
                if (size>3){
                    historyAmount3.setText(model.getAmount().get(size-3));
                    historyTime3.setText(model.getTime().get(size-3));
                }
            }


        }
    }
}
