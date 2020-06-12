package com.kelsiraman.peminum;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.kelsiraman.peminum.config.Konfigurasi;
import com.kelsiraman.peminum.mainlayout.MainActivity;
import com.kelsiraman.peminum.model.DataUser;

public class loginActivity extends AppCompatActivity {
    private static final String PARCEL = "DATAUSER";
    private DataUser parcelDU;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private String UID, valuePassword, parcelEmail, parcelUsername, parcelPassword, parcelGender, parcelBangun, parcelTidur;
    private int parcelBerat;
    private EditText email, password;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(Konfigurasi.LOGINPREF,MODE_PRIVATE);
        if (sp.getBoolean(Konfigurasi.LOGGED, false)){
            parcelEmail = sp.getString(Konfigurasi.EMAIL,"undefined");
            parcelUsername = sp.getString(Konfigurasi.USERNAME,"undefined");
            parcelPassword = sp.getString(Konfigurasi.PASSWORD, "undefined");
            parcelGender = sp.getString(Konfigurasi.GENDER,"undefined");
            parcelBangun = sp.getString(Konfigurasi.BANGUN,"undefined");
            parcelTidur = sp.getString(Konfigurasi.TIDUR,"undefined");
            parcelBerat = sp.getInt(Konfigurasi.BERAT,0);
            UID = sp.getString(Konfigurasi.UID,"undefined");
            moveToMain();
        }else {
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
                    valuePassword = password.getText().toString();
                    if(valueEmail.isEmpty() || valuePassword.isEmpty()){
                        Toast.makeText(loginActivity.this, "Form Tidak Lengkap", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginAccount(valueEmail, valuePassword);
                }
            });
        }
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
                            Toast.makeText(loginActivity.this,"Berhasil Masuk",Toast.LENGTH_SHORT).show();
                            fetchDataAndToMain();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity.this,"Gagal Login Akun",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchDataAndToMain() {
        FirebaseUser user = mAuth.getCurrentUser();
        parcelEmail = user.getEmail();
        UID = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference root = ref.child("DataUser").child(mAuth.getUid()).child("Profil");

        root.addListenerForSingleValueEvent(new ValueEventListener() {
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
                saveDatePref();
                moveToMain();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void saveDatePref() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Konfigurasi.USERNAME,parcelUsername);
        editor.putString(Konfigurasi.EMAIL,parcelEmail);
        editor.putString(Konfigurasi.PASSWORD, valuePassword);
        editor.putString(Konfigurasi.GENDER, parcelGender);
        editor.putString(Konfigurasi.BANGUN,parcelBangun);
        editor.putString(Konfigurasi.TIDUR,parcelTidur);
        editor.putInt(Konfigurasi.BERAT,parcelBerat);
        editor.putBoolean(Konfigurasi.LOGGED,true);
        editor.putString(Konfigurasi.UID,UID);
        editor.apply();
    }

    private void moveToMain() {
        DataUser du = new DataUser(parcelEmail, parcelUsername, parcelGender, parcelBangun, parcelTidur, parcelBerat);
        Intent main = new Intent(getBaseContext(), MainActivity.class);
        main.putExtra(PARCEL, du);
        main.putExtra(Konfigurasi.UID,UID);
        startActivity(main);
    }

    public void moveToSignUp(){
        Intent intent  = new Intent(getBaseContext(),SignUp.class);
        startActivity(intent);
    }
}
