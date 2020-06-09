package com.kelsiraman.peminum;

import androidx.appcompat.app.AppCompatActivity;

import com.kelsiraman.peminum.config.Konfigurasi;
import com.kelsiraman.peminum.onboardingscreen.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp = getSharedPreferences(Konfigurasi.SPLASHPREF,MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getBoolean(Konfigurasi.FIRSTTIME,true)){
                    startActivity(new Intent(SplashScreen.this,onBoardingActivity.class));
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(Konfigurasi.FIRSTTIME, false);
                    editor.apply();
                }
                else {
                    startActivity(new Intent(SplashScreen.this,loginActivity.class));
                }

            }
        },2000);
    }
}
