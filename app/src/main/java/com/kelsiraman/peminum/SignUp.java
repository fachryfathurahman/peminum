package com.kelsiraman.peminum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kelsiraman.peminum.model.DataUser;
import com.kelsiraman.peminum.model.SignUpModel;
import com.kelsiraman.peminum.pendaftaran.GenderActivity;

public class SignUp extends AppCompatActivity {
    private static final String PARCEL = "DATAUSER";
    private String valueEmail, valueUsername, valuePassword;
    private EditText username, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.suUsername);
        email = findViewById(R.id.suEmail);
        password = findViewById(R.id.suPassword);
        confirmPassword = findViewById(R.id.suConfirmPassword);
        Button signUp = findViewById(R.id.signupButton);
        TextView signIn = findViewById(R.id.moveToSignIn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputForm();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSignIn();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void inputForm() {
        valueUsername = username.getText().toString();
        valueEmail = email.getText().toString();
        valuePassword = password.getText().toString();
        String valueConfirmPassword = confirmPassword.getText().toString();

        if (valueUsername.isEmpty() || valueEmail.isEmpty() || valuePassword.isEmpty() || valueConfirmPassword.isEmpty()) {
            Toast.makeText(SignUp.this, "Form Tidak Lengkap", Toast.LENGTH_SHORT).show();
            return;
        }
        if(valuePassword.length() < 7){
            Toast.makeText(SignUp.this, "Password harus di atas 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(valuePassword.equals(valueConfirmPassword))){
            Toast.makeText(SignUp.this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
            confirmPassword.setText("");
            return;
        }
        moveToGenderActivity();
    }

    private void moveToGenderActivity() {
        DataUser parcelDU = new DataUser(valueEmail, valueUsername, null, null, null, 0);
        Intent gender = new Intent(getBaseContext(), GenderActivity.class);
        gender.putExtra(PARCEL, parcelDU);
        SignUpModel parcelSU = new SignUpModel(valueEmail, valuePassword);
        gender.putExtra("PARCELSU", parcelSU);
        startActivity(gender);
    }

    public void moveToSignIn(){
        Intent signIn = new Intent(getBaseContext(),loginActivity.class);
        startActivity(signIn);
    }

}
