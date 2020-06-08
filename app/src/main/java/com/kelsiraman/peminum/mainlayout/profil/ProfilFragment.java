package com.kelsiraman.peminum.mainlayout.profil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kelsiraman.peminum.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    private EditText pUsername, pBerat, pEmail;

    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profil, container, false);
        pUsername = view.findViewById(R.id.profilUsername);
        pBerat = view.findViewById(R.id.profilBerat);
        pEmail = view.findViewById(R.id.profilEmail);

        fetchData();
        return view;
    }

    private void fetchData() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("DataUser").child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pUsername.setText(dataSnapshot.child("username").getValue(String.class));
                        pBerat.setText(dataSnapshot.child("userBerat").getValue(String.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        FirebaseUser user = auth.getCurrentUser();
        pEmail.setText(user.getEmail());
    }

    public static ProfilFragment newInstance(){
        ProfilFragment fragment = new ProfilFragment();
        return fragment;
    }
}
