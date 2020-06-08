package com.kelsiraman.peminum.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataUser implements Parcelable {
    private String userEmail, username, userGender, userBangun, userTidur;
    private int userBerat;

    public DataUser(){

    }

    public DataUser(String userEmail, String username, String userGender, String userBangun, String userTidur, int userBerat) {
        this.userEmail = userEmail;
        this.username = username;
        this.userGender = userGender;
        this.userBangun = userBangun;
        this.userTidur = userTidur;
        this.userBerat = userBerat;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserBangun() {
        return userBangun;
    }

    public void setUserBangun(String userBangun) {
        this.userBangun = userBangun;
    }

    public String getUserTidur() {
        return userTidur;
    }

    public void setUserTidur(String userTidur) {
        this.userTidur = userTidur;
    }

    public int getUserBerat() {
        return userBerat;
    }

    public void setUserBerat(int userBerat) {
        this.userBerat = userBerat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userEmail);
        dest.writeString(this.username);
        dest.writeString(this.userGender);
        dest.writeString(this.userBangun);
        dest.writeString(this.userTidur);
        dest.writeInt(this.userBerat);
    }

    protected DataUser(Parcel in) {
        this.userEmail = in.readString();
        this.username = in.readString();
        this.userGender = in.readString();
        this.userBangun = in.readString();
        this.userTidur = in.readString();
        this.userBerat = in.readInt();
    }

    public static final Parcelable.Creator<DataUser> CREATOR = new Parcelable.Creator<DataUser>() {
        @Override
        public DataUser createFromParcel(Parcel source) {
            return new DataUser(source);
        }

        @Override
        public DataUser[] newArray(int size) {
            return new DataUser[size];
        }
    };
}
