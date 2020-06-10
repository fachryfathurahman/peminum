package com.kelsiraman.peminum.pendaftaran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.MainActivity;
import com.kelsiraman.peminum.model.DataUser;
import com.kelsiraman.peminum.model.SignUpModel;

public class SleepTimeActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private static final String PARCEL = "DATAUSER";
    private String emailSU, passwordSU;
    private DataUser parcelDU, du;
    private FirebaseAuth mAuth;
    private TimePicker waktuTidur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time);
        mAuth = FirebaseAuth.getInstance();
        parcelDU = getIntent().getParcelableExtra(PARCEL);
        SignUpModel parcelSU = getIntent().getParcelableExtra("PARCELSU");
        emailSU = parcelSU.getEmail();
        passwordSU = parcelSU.getPassword();
        waktuTidur = findViewById(R.id.sleepTimePicker);
    }

    public void SleepBackButtonOnClick(View view){
        finish();
    }

    public void SleepNextButtonOnClick(View view){
        du = new DataUser(parcelDU.getUserEmail(), parcelDU.getUsername(), parcelDU.getUserGender(), parcelDU.getUserBangun(), getWaktuTidur(), parcelDU.getUserBerat());
        createAccount(emailSU, passwordSU);
    }

    private String getWaktuTidur() {
        int jamTidur = waktuTidur.getCurrentHour();
        int menitTidur = waktuTidur.getCurrentMinute();
        return jamTidur + ":" + menitTidur;
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pushToDatabase();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SleepTimeActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void pushToDatabase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String getUserID = auth.getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference root = ref.child("DataUser").child(getUserID).child("Profil");

        root.push()
                .setValue(new DataUser(parcelDU.getUserEmail(), parcelDU.getUsername(), parcelDU.getUserGender(), parcelDU.getUserBangun(), getWaktuTidur(), parcelDU.getUserBerat()))
                .addOnSuccessListener(this, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(SleepTimeActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                        moveToMain();
                    }
                });
    }

    private void moveToMain() {
        Intent main = new Intent(getBaseContext(), MainActivity.class);
        main.putExtra(PARCEL, du);
        startActivity(main);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
