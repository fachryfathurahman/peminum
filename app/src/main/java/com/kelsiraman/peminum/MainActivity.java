package com.kelsiraman.peminum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView registrasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrasi =findViewById(R.id.Register);
        registrasi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.Register){
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
