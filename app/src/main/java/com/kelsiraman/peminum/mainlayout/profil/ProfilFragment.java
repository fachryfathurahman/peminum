package com.kelsiraman.peminum.mainlayout.profil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.config.Konfigurasi;
import com.kelsiraman.peminum.loginActivity;
import com.kelsiraman.peminum.model.DataUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener {
    private static final String PARCEL = "DATAUSER";
    private DataUser parcelDU;
    private Button saveBtn, signOut;
    private EditText pUsername, pBerat, pEmail;
    private Context mContext;
    FloatingActionButton fab;
    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        parcelDU = getActivity().getIntent().getParcelableExtra(PARCEL);
        pUsername = view.findViewById(R.id.profilUsername);
        pBerat = view.findViewById(R.id.profilBerat);
        pEmail = view.findViewById(R.id.profilEmail);
        pUsername.setText(parcelDU.getUsername());
        pBerat.setText(parcelDU.getUserBerat() + "");
        pEmail.setText(parcelDU.getUserEmail());

        signOut = view.findViewById(R.id.signOut);
        signOut.setOnClickListener(this);

        saveBtn = view.findViewById(R.id.buttonSave);
        saveBtn.setOnClickListener(this);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void sendUpdateProfile() {
        final String username = pUsername.getText().toString();
        final int berat = Integer.parseInt(pBerat.getText().toString());
        final String email = pEmail.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String getUserID = auth.getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference root = ref.child("DataUser").child(getUserID).child("Profil");

        Query query = root.orderByChild("userEmail").equalTo(parcelDU.getUserEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.child("username").getRef().setValue(username);
                    ds.child("userBerat").getRef().setValue(berat);
                    ds.child("userEmail").getRef().setValue(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("PUSHING", databaseError.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    public static ProfilFragment newInstance(){
        return new ProfilFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signOut:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                moveToSignIn();
                break;
            case R.id.fab:
                pUsername.setEnabled(true);
                pBerat.setEnabled(true);
                pEmail.setEnabled(true);
                saveBtn.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
                break;
            case R.id.buttonSave:
                pUsername.setEnabled(false);
                pBerat.setEnabled(false);
                pEmail.setEnabled(false);
                saveBtn.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);
                sendUpdateProfile();
                break;
        }
    }

    private void moveToSignIn() {
        SharedPreferences sp = getActivity().getSharedPreferences(Konfigurasi.LOGINPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Konfigurasi.LOGGED,false);
        editor.apply();
        Intent intent = new Intent(mContext, loginActivity.class );
        startActivity(intent);
        getActivity().finish();
    }
}
