package com.kelsiraman.peminum.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class HistoryRvModel implements Parcelable {
    private String day;
    private ArrayList<String>amount, time;

    protected HistoryRvModel(Parcel in) {
        day = in.readString();
        amount = in.createStringArrayList();
        time = in.createStringArrayList();
    }
    public HistoryRvModel(){}
    public HistoryRvModel(String day, ArrayList<String> amount, ArrayList<String> time){
        this.day =day;
        this.amount = amount;
        this.time = time;
    }

    public static final Creator<HistoryRvModel> CREATOR = new Creator<HistoryRvModel>() {
        @Override
        public HistoryRvModel createFromParcel(Parcel in) {
            return new HistoryRvModel(in);
        }

        @Override
        public HistoryRvModel[] newArray(int size) {
            return new HistoryRvModel[size];
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