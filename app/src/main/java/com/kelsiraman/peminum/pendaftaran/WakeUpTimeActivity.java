package com.kelsiraman.peminum.pendaftaran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.DataUser;
import com.kelsiraman.peminum.model.SignUpModel;

public class WakeUpTimeActivity extends AppCompatActivity {
    private static final String PARCEL = "DATAUSER";
    private DataUser parcelDU;
    private TimePicker waktuBangun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up_time);
        parcelDU = getIntent().getParcelableExtra(PARCEL);
        waktuBangun = findViewById(R.id.wakeUpTimePicker);
    }

    public void WakeUpBackButtonOnClick(View view){
        finish();
    }

    public void WakeUpNextButtonOnClick(View view){
        DataUser du = new DataUser(parcelDU.getUserEmail(), parcelDU.getUsername(), parcelDU.getUserGender(), getWaktuBangun(), null, parcelDU.getUserBerat());
        Intent intent = new Intent(this, SleepTimeActivity.class);
        intent.putExtra(PARCEL, du);
        SignUpModel parcelSU = getIntent().getParcelableExtra("PARCELSU");
        parcelSU = new SignUpModel(parcelSU.getEmail(), parcelSU.getPassword());
        intent.putExtra("PARCELSU", parcelSU);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private String getWaktuBangun() {
        int jamBangun = waktuBangun.getCurrentHour();
        int menitBangun = waktuBangun.getCurrentMinute();
        return jamBangun + ":" + menitBangun;
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
