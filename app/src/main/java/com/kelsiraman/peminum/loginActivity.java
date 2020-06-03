package com.kelsiraman.peminum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    private TextView moveToSignUp;
    private Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        moveToSignUp =  findViewById(R.id.moveToSignup);
        signIn = findViewById(R.id.signInButton);

        moveToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               moveToSignUp();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void moveToSignUp(){
        Intent intent  = new Intent(getBaseContext(),SignUp.class);
        startActivity(intent);
    }


}
