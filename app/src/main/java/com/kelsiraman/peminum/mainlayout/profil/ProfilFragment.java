package com.kelsiraman.peminum.mainlayout.profil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelsiraman.peminum.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    public static ProfilFragment newInstance(){
        ProfilFragment fragment = new ProfilFragment();

        return fragment;

    }
}
