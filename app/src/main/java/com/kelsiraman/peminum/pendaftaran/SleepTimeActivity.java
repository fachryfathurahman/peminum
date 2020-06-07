package com.kelsiraman.peminum.pendaftaran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.MainActivity;
import com.kelsiraman.peminum.model.DataUser;

public class SleepTimeActivity extends AppCompatActivity {
    private DataUser parcelDU, du;
    private static final String PARCEL = "DATAUSER";
    private TimePicker waktuTidur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time);

        parcelDU = getIntent().getParcelableExtra(PARCEL);
        waktuTidur = findViewById(R.id.sleepTimePicker);
    }

    public void SleepBackButtonOnClick(View view){
        finish();
    }

    public void SleepNextButtonOnClick(View view){
        du = new DataUser(parcelDU.getUsername(), parcelDU.getUserGender(), parcelDU.getUserBangun(), getWaktuTidur(), parcelDU.getUserBerat());
        pushToDatabase();
        moveToMain();
    }

    private String getWaktuTidur() {
        int jamTidur = waktuTidur.getCurrentHour();
        int menitTidur = waktuTidur.getCurrentMinute();
        String ampm;

        if(jamTidur > 12) {
            jamTidur -= 12;
            ampm = "PM";
        }
        else {
            ampm = "AM";
        }
        return jamTidur + ":" + menitTidur + " " + ampm;
    }

    private void pushToDatabase() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String getUserID = auth.getCurrentUser().getUid();

//        database.child("Data User").child(getUserID).child("Profil User").push()
//                .setValue(new DataUser(du.getUsername(), du.getUserGender(), du.getUserBangun(), du.getUserTidur(), du.getUserBerat()))
//                .addOnSuccessListener(this, new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
//                        Toast.makeText(SleepTimeActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private void moveToMain() {
        Intent main = new Intent(getBaseContext(), MainActivity.class);
        main.putExtra(PARCEL, du);
        startActivity(main);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
