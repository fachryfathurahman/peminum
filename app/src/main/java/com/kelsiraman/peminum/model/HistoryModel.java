package com.kelsiraman.peminum.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class HistoryModel implements Parcelable {
    private String day;
    private ArrayList<String>amount, time;

    protected HistoryModel(Parcel in) {
        day = in.readString();
        amount = in.createStringArrayList();
        time = in.createStringArrayList();
    }
    public HistoryModel(){}
    public HistoryModel(String day, ArrayList<String> amount, ArrayList<String> time){
        this.day =day;
        this.amount = amount;
        this.time = time;
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<String> getAmount() {
        return amount;
    }

    public void setAmount(ArrayList<String> amount) {
        this.amount = amount;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(day);
        parcel.writeStringList(amount);
        parcel.writeStringList(time);
    }
}
