package com.kelsiraman.peminum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kelsiraman.peminum.mainlayout.MainActivity;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;

    private TextView signIn;
    private EditText username, email, password, confirmPassword;
    private Button signUp;
    private String valueUsername, valueEmail, valuePassword, valueConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.suUsername);
        email = findViewById(R.id.suEmail);
        password = findViewById(R.id.suPassword);
        confirmPassword = findViewById(R.id.suConfirmPassword);
        signUp = findViewById(R.id.signupButton);
        signIn = findViewById(R.id.moveToSignIn);

        valueUsername = username.getText().toString();
        valueEmail = email.getText().toString();
        valuePassword = password.getText().toString();
        valueConfirmPassword = confirmPassword.getText().toString();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!valuePassword.equals(valueConfirmPassword)){
                    Log.d(TAG, "createAccount:" + valuePassword + " || " + valueConfirmPassword);
                    //TODO: tambahkan peringatan password tidak sama
                    return;
                }
                createAccount(valueEmail, valuePassword);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moveToSignIn();
                onBackPressed();
            }
        });


    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (valueUsername.isEmpty() || valueEmail.isEmpty() || valuePassword.isEmpty() || valueConfirmPassword.isEmpty()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            moveToMain();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void moveToSignIn(){
        Intent signIn = new Intent(getBaseContext(),loginActivity.class);
        startActivity(signIn);
    }

    public void moveToMain(){
        Intent main = new Intent(getBaseContext(), MainActivity.class);
        startActivity(main);
    }
}
