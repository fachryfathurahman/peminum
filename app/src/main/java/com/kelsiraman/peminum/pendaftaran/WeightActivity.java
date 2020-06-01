package com.kelsiraman.peminum.pendaftaran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.kelsiraman.peminum.R;

public class WeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        NumberPicker weightPicker = findViewById(R.id.weightPicker);
        weightPicker.setMaxValue(100);
        weightPicker.setMinValue(1);
        weightPicker.setValue(50);
    }

    public void WeightBackButtonOnClick(View view){
        finish();
    }

    public void WeightNextButtonOnClick(View view){
        Intent intent = new Intent(this, WakeUpTimeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
