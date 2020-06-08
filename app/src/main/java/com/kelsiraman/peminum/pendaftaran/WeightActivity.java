package com.kelsiraman.peminum.pendaftaran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.DataUser;

public class WeightActivity extends AppCompatActivity {
    private static final String PARCEL = "DATAUSER";
    private DataUser parcelDU;
    private NumberPicker weightPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        parcelDU = getIntent().getParcelableExtra(PARCEL);
        weightPicker = findViewById(R.id.weightPicker);
        weightPicker.setMaxValue(150);
        weightPicker.setMinValue(1);
        weightPicker.setValue(50);
    }

    public void WeightBackButtonOnClick(View view){
        finish();
    }

    public void WeightNextButtonOnClick(View view){
        DataUser du = new DataUser(parcelDU.getUserEmail(), parcelDU.getUsername(), parcelDU.getUserGender(), null, null, weightPicker.getValue());
        Intent intent = new Intent(this, WakeUpTimeActivity.class);
        intent.putExtra(PARCEL, du);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
