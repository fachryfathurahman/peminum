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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kelsiraman.peminum.mainlayout.MainActivity;
import com.kelsiraman.peminum.model.DataUser;

public class loginActivity extends AppCompatActivity {
    private static final String PARCEL = "DATAUSER";
    private DataUser parcelDU;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private String parcelEmail, parcelUsername, parcelGender, parcelBangun, parcelTidur;
    private int parcelBerat;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.siEmail);
        password = findViewById(R.id.siPassword);
        TextView moveToSignUp =  findViewById(R.id.moveToSignup);
        Button signIn = findViewById(R.id.signInButton);
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
                    return;
                }
                loginAccount(valueEmail, valuePassword);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void loginAccount(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            fetchDataAndToMain();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchDataAndToMain() {
        FirebaseUser user = mAuth.getCurrentUser();
        parcelEmail = user.getEmail();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("DataUser").child(mAuth.getUid()).child("Profil").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    parcelDU = data.getValue(DataUser.class);
                    parcelUsername = parcelDU.getUsername();
                    parcelGender = parcelDU.getUserGender();
                    parcelBerat = parcelDU.getUserBerat();
                    parcelBangun = parcelDU.getUserBangun();
                    parcelTidur = parcelDU.getUserTidur();
                }
                moveToMain();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void moveToMain() {
        Log.d("PUSH", parcelDU.getUsername() + " " + parcelDU.getUserBerat() + " " + parcelDU.getUserBerat() + " " + parcelDU.getUserBangun() + " " + parcelDU.getUserTidur());
        DataUser du = new DataUser(parcelEmail, parcelUsername, parcelGender, parcelBangun, parcelTidur, parcelBerat);
        Intent main = new Intent(getBaseContext(), MainActivity.class);
        main.putExtra(PARCEL, du);
        startActivity(main);
    }

    public void moveToSignUp(){
        Intent intent  = new Intent(getBaseContext(),SignUp.class);
        startActivity(intent);
    }
}
