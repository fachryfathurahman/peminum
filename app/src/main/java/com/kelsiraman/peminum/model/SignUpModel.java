package com.kelsiraman.peminum.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SignUpModel implements Parcelable {
    private String email, password;

    public SignUpModel() {
    }

    public SignUpModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.password);
    }

    protected SignUpModel(Parcel in) {
        this.email = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<SignUpModel> CREATOR = new Parcelable.Creator<SignUpModel>() {
        @Override
        public SignUpModel createFromParcel(Parcel source) {
            return new SignUpModel(source);
        }

        @Override
        public SignUpModel[] newArray(int size) {
            return new SignUpModel[size];
        }
    };
}
