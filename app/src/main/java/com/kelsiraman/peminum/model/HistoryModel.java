package com.kelsiraman.peminum.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class HistoryModel implements Parcelable {
    private String day;
    private ArrayList<String>amount, time;

    public HistoryModel(){

    }

    public HistoryModel(String day, ArrayList<String> amount, ArrayList<String> time) {
        this.day = day;
        this.amount = amount;
        this.time = time;
    }

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.day);
        dest.writeStringList(this.amount);
        dest.writeStringList(this.time);
    }

    protected HistoryModel(Parcel in) {
        this.day = in.readString();
        this.amount = in.createStringArrayList();
        this.time = in.createStringArrayList();
    }

    public static final Parcelable.Creator<HistoryModel> CREATOR = new Parcelable.Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel source) {
            return new HistoryModel(source);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };
}
