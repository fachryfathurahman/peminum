package com.kelsiraman.peminum.mainlayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.ui.main.SectionsPagerAdapter;
import com.kelsiraman.peminum.model.DataUser;

public class MainActivity extends AppCompatActivity {
    private DataUser parcelDU;
    private static final String PARCEL = "DATAUSER";
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
        setContentView(R.layout.activity_main);
        mContext = this;
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setTabIcons();

        parcelDU = getIntent().getParcelableExtra(PARCEL);
        Log.d("Data User", parcelDU.getUsername() + ", " + parcelDU.getUserGender() + ", " + parcelDU.getUserBerat() + ", " + parcelDU.getUserBangun() + ", " + parcelDU.getUserTidur());

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ((position == 0)) {
                    fab.hide();
                } else if (position == 1){
                    fab.show();
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO tempat menaruh fungsi fab history
                        }
                    });
                }else{
                    fab.show();
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO tempat menaruh fungsi fab profil
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setTabIcons(){
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
    }
}