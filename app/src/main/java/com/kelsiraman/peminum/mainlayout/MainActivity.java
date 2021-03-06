package com.kelsiraman.peminum.mainlayout;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabs;
    private Context mContext;
    private int[] tabIcons = {
            R.drawable.ic_drop,
            R.drawable.ic_history,
            R.drawable.ic_user
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setTabIcons();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        finishAffinity();
    }

    public void setTabIcons(){
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
    }
}