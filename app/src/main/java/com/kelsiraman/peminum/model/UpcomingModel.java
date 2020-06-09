package com.kelsiraman.peminum.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UpcomingModel implements Parcelable {
    String amount, time;

    protected UpcomingModel(Parcel in) {
        amount = in.readString();
        time = in.readString();
    }
    public static final Creator<UpcomingModel> CREATOR = new Creator<UpcomingModel>() {
        @Override
        public UpcomingModel createFromParcel(Parcel in) {
            return new UpcomingModel(in);
        }

        @Override
        public UpcomingModel[] newArray(int size) {
            return new UpcomingModel[size];
        }
    };

    public UpcomingModel(String amount, String time) {
        this.amount = amount;
        this.time = time;
    }

    public UpcomingModel() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(amount);
        parcel.writeString(time);
    }
}
