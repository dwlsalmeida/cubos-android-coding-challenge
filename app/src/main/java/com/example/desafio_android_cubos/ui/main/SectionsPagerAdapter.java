package com.example.desafio_android_cubos.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.desafio_android_cubos.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1,
                                                      R.string.tab_text_2,
                                                      R.string.tab_text_3,
                                                      R.string.tab_text_4};
    private final Context mContext;
    private final List<Fragment> fragments = new ArrayList<Fragment>(Arrays.asList(
            MovieFragment.newInstance(1),
            MovieFragment.newInstance(2),
            MovieFragment.newInstance(3),
            MovieFragment.newInstance(4)
    ));

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return fragments.size();
    }
}