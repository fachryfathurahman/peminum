package com.kelsiraman.peminum.mainlayout.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.mainlayout.history.HistoryFragment;
import com.kelsiraman.peminum.mainlayout.home.HomeFragment;
import com.kelsiraman.peminum.mainlayout.profil.ProfilFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.home_tab_text, R.string.history_tab_text,R.string.profile_tab_text};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 1: return HistoryFragment.newInstance();
            case 2: return ProfilFragment.newInstance();
            case 0 :
            default:return HomeFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {

        return 3;
    }
}