package com.kelsiraman.peminum.mainlayout.profil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kelsiraman.peminum.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profil, container, false);
//        EditText pUsername = view.findViewById(R.id.profilUsername);
//        EditText pBerat = view.findViewById(R.id.profilBerat);
//        EditText pEmail = view.findViewById(R.id.profilEmail);

        return view;
    }

    public static ProfilFragment newInstance(){
        ProfilFragment fragment = new ProfilFragment();
        return fragment;
    }
}
