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

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HolderHistory>{
    private ArrayList<HistoryRvModel> historyData;
    private Context mContext;

    public void setHistoryData(ArrayList<HistoryRvModel> data,Context context){
        this.historyData = data;
        this.mContext=context;
    }
    @NonNull
    @Override
    public HolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_history,parent,false);
        return new HolderHistory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderHistory holder, int position) {
        HistoryRvModel model = historyData.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }

    public class HolderHistory extends RecyclerView.ViewHolder {
        TextView amount,time;
        ImageView img;
        View div;
        public HolderHistory(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amountHstr);
            time = itemView.findViewById(R.id.timeHstr);
            img = itemView.findViewById(R.id.img);
            div = itemView.findViewById(R.id.view2);
        }
        public void bind(HistoryRvModel historyRvModel){
            int size = historyRvModel.getAmount().size();
            amount.setText(historyRvModel.getAmount().get(size));
            time.setText(historyRvModel.getTime().get(size));
        }
    }
}
