package com.kelsiraman.peminum.mainlayout.profil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.DataUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    private static final String PARCEL = "DATAUSER";
    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profil, container, false);
        DataUser parcelDU = getActivity().getIntent().getParcelableExtra(PARCEL);
        EditText pUsername = view.findViewById(R.id.profilUsername);
        EditText pBerat = view.findViewById(R.id.profilBerat);
        EditText pEmail = view.findViewById(R.id.profilEmail);
        pUsername.setText(parcelDU.getUsername());
        pBerat.setText(parcelDU.getUserBerat() + "");
        pEmail.setText(parcelDU.getUserEmail());
        return view;
    }

    public static ProfilFragment newInstance(){
        ProfilFragment fragment = new ProfilFragment();
        return fragment;
    }
}
