package com.kelsiraman.peminum.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryModel implements Parcelable {
    private String day, amount, time;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public HistoryModel(){

    }

    public HistoryModel(String day, String amount, String time) {
        this.day = day;
        this.amount = amount;
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.day);
        dest.writeString(this.amount);
        dest.writeString(this.time);
    }

    protected HistoryModel(Parcel in) {
        this.day = in.readString();
        this.amount = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<HistoryRvModel> CREATOR = new Parcelable.Creator<HistoryRvModel>() {
        @Override
        public HistoryRvModel createFromParcel(Parcel source) {
            return new HistoryRvModel(source);
        }

        @Override
        public HistoryRvModel[] newArray(int size) {
            return new HistoryRvModel[size];
        }
    };
}
