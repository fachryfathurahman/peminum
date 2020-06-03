package com.kelsiraman.peminum;

import androidx.appcompat.app.AppCompatActivity;
import com.kelsiraman.peminum.onboardingscreen.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,onBoardingActivity.class));
            }
        },2000);
    }
}
