package com.kelsiraman.peminum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kelsiraman.peminum.mainlayout.MainActivity;

public class loginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private Button signIn;
    private EditText email, password;
    private TextView moveToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.siEmail);
        password = findViewById(R.id.siPassword);
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
                String valueEmail = email.getText().toString();
                String valuePassword = password.getText().toString();

                if(valueEmail.isEmpty() || valuePassword.isEmpty()){
                    Toast.makeText(loginActivity.this, "Form Tidak Lengkap", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Form Gak Lengkap");
                    return;
                }
                loginAccount(valueEmail, valuePassword);
            }
        });

    }

    public void loginAccount(String email, String password){
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            moveToMain();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void moveToSignUp(){
        Intent intent  = new Intent(getBaseContext(),SignUp.class);
        startActivity(intent);
    }

    public void moveToMain(){
        Intent main = new Intent(getBaseContext(), MainActivity.class);
        startActivity(main);
    }
}
